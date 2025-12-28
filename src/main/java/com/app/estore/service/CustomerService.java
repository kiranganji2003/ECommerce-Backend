package com.app.estore.service;

import com.app.estore.request.CartRequestDto;
import com.app.estore.response.*;
import com.app.estore.utility.ProductCategory;
import com.app.estore.request.RegistrationDto;

public interface CustomerService {
    Status registerCustomer(RegistrationDto customerRegistrationDto);
    CustomerProductResponse findAllProducts();
    CustomerProductResponse findProductsByCostRange(Integer min, Integer max);
    CustomerProductResponse getProductsByCategory(ProductCategory category);
    CustomerProfileDto getCustomerProfile();
    CustomerProductDto getProductById(Integer productId);
    Status addProductInWishlist(Integer productId);
    CustomerProductResponse getWishlistProducts();
    Status removeProductFromWishlist(Integer productId);
    Status updateProductToCart(CartRequestDto cartRequestDto);
    CartResponseDto viewCart();
}
