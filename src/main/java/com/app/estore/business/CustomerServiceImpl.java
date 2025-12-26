package com.app.estore.business;

import com.app.estore.response.ListProductResponse;
import com.app.estore.utility.ProductCategory;
import com.app.estore.utility.ProductModelMapper;
import com.app.estore.entity.AllProducts;
import com.app.estore.entity.Customer;
import com.app.estore.entity.Product;
import com.app.estore.repository.AllProductsRepository;
import com.app.estore.repository.CustomerRepository;
import com.app.estore.repository.ProductRepository;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.CustomerProductDto;
import com.app.estore.response.Status;
import com.app.estore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.app.estore.utility.EStoreConstants.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AllProductsRepository allProductsRepository;
    private final ProductRepository productRepository;
    private final ProductModelMapper productModelMapper;

    @Override
    public Status registerCustomer(RegistrationDto registrationDto) {
        Customer customer = new Customer();
        customer.setEmail(registrationDto.getEmail());
        customer.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        customer.setName(registrationDto.getName());
        customer.setPhone(registrationDto.getPhone());
        customerRepository.save(customer);
        return new Status(REGISTERED_SUCCESSFULLY);
    }

    @Override
    public ListProductResponse findAllProducts() {

        List<AllProducts> allProductsList = allProductsRepository.findAll();
        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(AllProducts allProducts : allProductsList) {
            Product product = productRepository.getReferenceById(allProducts.getProductId());
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new ListProductResponse(customerProductDtoList);
    }

    @Override
    public ListProductResponse findProductsByCostRange(Integer min, Integer max) {

        List<AllProducts> allProductsList = allProductsRepository.findProductsByCostRange(min, max);

        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(AllProducts allProducts : allProductsList) {
            Product product = productRepository.getReferenceById(allProducts.getProductId());
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new ListProductResponse(customerProductDtoList);
    }

    @Override
    public ListProductResponse getProductsByCategory(ProductCategory category) {
        List<AllProducts> allProductsList = allProductsRepository.findByProductCategory(category);

        List<CustomerProductDto> customerProductDtoList = new ArrayList<>();

        for(AllProducts allProducts : allProductsList) {
            Product product = productRepository.getReferenceById(allProducts.getProductId());
            customerProductDtoList.add(productModelMapper.convert(product));
        }

        return new ListProductResponse(customerProductDtoList);
    }

}
