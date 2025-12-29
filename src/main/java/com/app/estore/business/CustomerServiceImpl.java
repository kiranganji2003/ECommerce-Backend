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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.app.estore.utility.EStoreConstants.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AllProductsRepository allProductsRepository;
    private final ProductRepository productRepository;
    private final ProductModelMapper productModelMapper;
    private final CustomerModelMapper customerModelMapper;
    private final CurrentUser currentUser;
    private final WishlistRepository wishlistRepository;
    private final CustomerCartRepository customerCartRepository;

    @Override
    @Transactional
    public Status registerCustomer(RegistrationDto registrationDto) {
        Customer customer = new Customer();
        customer.setEmail(registrationDto.getEmail());
        customer.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        customer.setName(registrationDto.getName());
        customer.setPhone(registrationDto.getPhone());
        customer = customerRepository.save(customer);

        Wishlist wishlist = new Wishlist();
        wishlist.setCustomerId(customer.getCustomerId());
        wishlistRepository.save(wishlist);

        CustomerCart customerCart = new CustomerCart();
        customerCart.setCustomerId(customer.getCustomerId());
        customerCartRepository.save(customerCart);

        return new Status(SUCCESS);
    }

    @Override
    public CustomerProductResponse findAllProducts() {

        List<AllProducts> allProductsList = allProductsRepository.findAll();
        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(AllProducts allProducts : allProductsList) {
            Product product = productRepository.getReferenceById(allProducts.getProductId());
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    @Override
    public CustomerProductResponse findProductsByCostRange(Integer min, Integer max) {

        List<AllProducts> allProductsList = allProductsRepository.findProductsByCostRange(min, max);

        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(AllProducts allProducts : allProductsList) {
            Product product = productRepository.getReferenceById(allProducts.getProductId());
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    @Override
    public CustomerProductResponse getProductsByCategory(ProductCategory category) {
        List<AllProducts> allProductsList = allProductsRepository.findByProductCategory(category);

        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(AllProducts allProducts : allProductsList) {
            Product product = productRepository.getReferenceById(allProducts.getProductId());
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    @Override
    public CustomerProfileDto getCustomerProfile() {
        Customer customer = customerRepository.findByEmail(currentUser.getCurrentUsername()).get();
        return customerModelMapper.convertToCustomerDto(customer);
    }

    @Override
    public CustomerProductDto getProductById(Integer productId) {
        Product product = productRepository.getReferenceById(productId);
        return productModelMapper.convert(product);
    }

    @Override
    @Transactional
    public Status addProductInWishlist(Integer productId) {

        if(productRepository.findById(productId).isEmpty()) {
            return new Status(FAILURE);
        }

        Wishlist wishlist = wishlistRepository.findById(getCustomerId()).get();
        wishlist.getProductIdSet().add(productId);
        return new Status(SUCCESS);
    }

    @Override
    @Transactional
    public CustomerProductResponse getWishlistProducts() {

        Wishlist wishlist = wishlistRepository.findById(getCustomerId()).get();
        Set<Integer> productIdSet = wishlist.getProductIdSet();
        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();
        Set<Integer> removeProductsSet = new HashSet<>();

        for(Integer productId : productIdSet) {
            Optional<Product> product = productRepository.findById(productId);

            if(product.isEmpty()) {
                removeProductsSet.add(productId);
            }
            else {
                customerProductDtoList.add(productModelMapper.convert(product.get()));
            }

        }

        for(Integer productId : removeProductsSet) {
            wishlist.getProductIdSet().remove(productId);
        }

        return new CustomerProductResponse(customerProductDtoList);
    }

    @Override
    @Transactional
    public Status removeProductFromWishlist(Integer productId) {
        Wishlist wishlist = wishlistRepository.findById(getCustomerId()).get();
        wishlist.getProductIdSet().remove(productId);
        return new Status(SUCCESS);
    }

    @Override
    @Transactional
    public Status updateProductToCart(CartRequestDto cartRequestDto) {

        Integer productId = cartRequestDto.getProductId();
        Integer productQuantity = cartRequestDto.getProductQuantity();
        CustomerCart customerCart = customerCartRepository.findById(getCustomerId()).get();

        if(productQuantity == 0) {
            customerCart.getProductToQuantityMap().remove(productId);
        }
        else {
            customerCart.getProductToQuantityMap().put(productId, productQuantity);
        }

        return new Status(SUCCESS);
    }

    @Override
    public CartResponseDto viewCart() {
        Map<Integer, Integer> productToQuantityMap = customerCartRepository.findById(getCustomerId()).get().getProductToQuantityMap();

        List<CartResponseDto.CartProduct> cartProductList = new ArrayList<>();
        Integer cartTotalCost = 0;

        for(Integer productId : productToQuantityMap.keySet()) {

            Optional<Product> productOptional = productRepository.findById(productId);

            if(productOptional.isEmpty()) {
                continue;
            }
            
            Product product = productOptional.get();

            CartResponseDto.CartProduct cartProduct = new CartResponseDto.CartProduct();

            cartProduct.setProductId(productId);
            cartProduct.setVendorId(product.getVendor().getVendorId());
            cartProduct.setTitle(product.getTitle());
            cartProduct.setDescription(product.getDescription());
            cartProduct.setWeight(product.getWeight());
            cartProduct.setDimensions(product.getDimensions());
            cartProduct.setCost(product.getCost());
            cartProduct.setProductCategory(product.getProductCategory());
            cartProduct.setProductQuantity(productToQuantityMap.get(productId));
            cartProduct.setTotalCost(cartProduct.getCost() * cartProduct.getProductQuantity());

            cartTotalCost += cartProduct.getTotalCost();
            cartProductList.add(cartProduct);
        }

        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setCartProductList(cartProductList);
        cartResponseDto.setCartTotalCost(cartTotalCost);

        return cartResponseDto;
    }

    private Integer getCustomerId() {
        return customerRepository.findByEmail(currentUser.getCurrentUsername()).get().getCustomerId();
    }

}
