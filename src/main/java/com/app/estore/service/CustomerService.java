package com.app.estore.service;

import com.app.estore.common.ProductCategory;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.AllProductsDto;
import com.app.estore.response.Status;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface CustomerService {
    Status registerCustomer(RegistrationDto customerRegistrationDto);
    List<AllProductsDto> findAllProducts();
    List<AllProductsDto> findProductsByCostRange(Integer min, Integer max);
    List<AllProductsDto> getProductsByCategory(ProductCategory category);
}
