package com.app.estore.business;

import com.app.estore.entity.*;
import com.app.estore.repository.*;
import com.app.estore.request.CartRequestDto;
import com.app.estore.response.*;
import com.app.estore.utility.CurrentUser;
import com.app.estore.utility.CustomerModelMapper;
import com.app.estore.utility.ProductCategory;
import com.app.estore.utility.ProductModelMapper;
import com.app.estore.request.RegistrationDto;
import com.app.estore.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.app.estore.utility.EStoreConstants.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ProductRepository productRepository;
    private final ProductModelMapper productModelMapper;
    private final CustomerModelMapper customerModelMapper;
    private final CurrentUser currentUser;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Status registerCustomer(RegistrationDto registrationDto) {
        Customer customer = new Customer();
        customer.setEmail(registrationDto.getEmail());
        customer.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        customer.setName(registrationDto.getName());
        customer.setPhone(registrationDto.getPhone());
        customerRepository.save(customer);
        return new Status(SUCCESS);
    }

    @Override
    public CustomerProductResponse findAllProducts() {

        List<Product> allProductsList = productRepository.findAll();
        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(Product product : allProductsList) {
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    @Override
    public CustomerProductResponse findProductsByCostRange(Integer min, Integer max) {

        if(min > max) {
            throw new RuntimeException("Invalid range exception " + min + " to " + max);
        }

        List<Product> allProductsList = productRepository.findProductsByCostRange(min, max);
        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(Product product : allProductsList) {
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    @Override
    public CustomerProductResponse getProductsByCategory(ProductCategory category) {
        List<Product> allProductsList = productRepository.findByProductCategory(category);
        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(Product product : allProductsList) {
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    @Override
    public CustomerProfileDto getCustomerProfile() {
        Customer customer = getCustomer();
        return customerModelMapper.convertToCustomerDto(customer);
    }

    @Override
    public CustomerProductDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        checkProductExist(product);
        return productModelMapper.convert(product.get());
    }

    private void checkProductExist(Optional<Product> product) {
        if(product.isEmpty()) {
            throw new RuntimeException("Product not found ");
        }
    }

    @Override
    @Transactional
    public Status addProductInWishlist(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        checkProductExist(product);

        Customer customer = getCustomer();
        customer.getWishlist().getProducts().add(product.get());
        return new Status(SUCCESS);
    }

    @Override
    public CustomerProductResponse getWishlistProducts() {
        Customer customer = getCustomer();
        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(Product product : customer.getWishlist().getProducts()) {
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    @Override
    @Transactional
    public Status removeProductFromWishlist(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        checkProductExist(product);

        Customer customer = getCustomer();
        customer.getWishlist().getProducts().remove(product.get());

        return new Status(SUCCESS);
    }

    @Override
    @Transactional
    public Status updateCartItem(CartRequestDto cartRequestDto) {

        Long productId = cartRequestDto.getProductId();
        Integer productQuantity = cartRequestDto.getProductQuantity();

        if(productQuantity < 0) {
            throw new RuntimeException("Negative product quantity");
        }

        Optional<Product> product = productRepository.findById(productId);
        checkProductExist(product);

        Customer customer = getCustomer();
        Cart cart = customer.getCart();
        Optional<CartItem> cartItemOptional = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId);

        if(cartItemOptional.isEmpty()) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product.get());
            cartItem.setProductQuantity(productQuantity);
            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }
        else if(productQuantity == 0) {
            cartItemRepository.delete(cartItemOptional.get());
        }
        else {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setProductQuantity(productQuantity);
        }

        return new Status(SUCCESS);
    }

    @Override
    public CartResponseDto viewCart() {
        Customer customer = getCustomer();
        Cart cart = customer.getCart();

        List<CartResponseDto.CartProduct> cartProductList = new ArrayList<>();
        Integer cartTotalCost = 0;

        for(CartItem cartItem : cart.getCartItems()) {

            Product product = cartItem.getProduct();

            CartResponseDto.CartProduct cartProduct = new CartResponseDto.CartProduct();

            cartProduct.setProductId(product.getProductId());
            cartProduct.setVendorId(product.getVendor().getVendorId());
            cartProduct.setTitle(product.getTitle());
            cartProduct.setDescription(product.getDescription());
            cartProduct.setWeight(product.getWeight());
            cartProduct.setDimensions(product.getDimensions());
            cartProduct.setCost(product.getCost());
            cartProduct.setProductCategory(product.getProductCategory());
            cartProduct.setProductQuantity(cartItem.getProductQuantity());
            cartProduct.setTotalCost(cartProduct.getCost() * cartProduct.getProductQuantity());

            cartTotalCost += cartProduct.getTotalCost();
            cartProductList.add(cartProduct);
        }

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartProductList(cartProductList);
        cartResponseDto.setCartTotalCost(cartTotalCost);

        return cartResponseDto;
    }

    @Override
    @Transactional
    public Status checkoutOrder() {

        Customer customer = getCustomer();
        Cart cart = customer.getCart();

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomer(customer);
        Integer totalCost = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setProductQuantity(cartItem.getProductQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getCost());
            orderItem.setItemTotalCost(orderItem.getPriceAtPurchase() * orderItem.getProductQuantity());
            totalCost += orderItem.getItemTotalCost();
            order.getItems().add(orderItem);
        }

        order.setTotalCost(totalCost);
        orderRepository.save(order);
        cart.getCartItems().clear();

        return new Status(SUCCESS);
    }

    @Override
    public OrderResponseDto showAllOrders() {
        Customer customer = getCustomer();
        List<Order> orderList = customer.getOrderList();

        return customerModelMapper.orderResponseDto(orderList);
    }

    @Override
    @Transactional
    public Status clearCart() {
        Customer customer = getCustomer();
        customer.getCart().getCartItems().clear();
        return new Status(SUCCESS);
    }

    @Override
    @Transactional
    public Status clearWishlist() {
        Customer customer = getCustomer();
        customer.getWishlist().getProducts().clear();
        return new Status(SUCCESS);
    }

    @Override
    public CustomerProductResponse getProductsByPages(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(Product product : productPage) {
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    private Customer getCustomer() {
        return customerRepository.findByEmail(currentUser.getCurrentUsername()).get();
    }

}
