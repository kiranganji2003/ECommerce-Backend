package com.app.estore.service;

import com.app.estore.response.CustomerProductDto;
import com.app.estore.response.CustomerProfileDto;
import com.app.estore.response.CustomerProductResponse;
import com.app.estore.utility.ProductCategory;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.Status;

public interface CustomerService {
    Status registerCustomer(RegistrationDto customerRegistrationDto);
    CustomerProductResponse findAllProducts();
    CustomerProductResponse findProductsByCostRange(Integer min, Integer max);
    CustomerProductResponse getProductsByCategory(ProductCategory category);
    CustomerProfileDto getCustomerProfile();
    CustomerProductDto getProductById(Integer productId);
}
