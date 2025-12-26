package com.app.estore.service;

import com.app.estore.response.ListProductResponse;
import com.app.estore.utility.ProductCategory;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.Status;

import java.util.List;

public interface CustomerService {
    Status registerCustomer(RegistrationDto customerRegistrationDto);
    ListProductResponse findAllProducts();
    ListProductResponse findProductsByCostRange(Integer min, Integer max);
    ListProductResponse getProductsByCategory(ProductCategory category);
}
