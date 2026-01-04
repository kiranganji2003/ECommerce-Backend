package com.app.estore.service;

import com.app.estore.request.CartRequestDto;
import com.app.estore.response.*;
import com.app.estore.utility.ProductCategory;
import com.app.estore.request.RegistrationDto;
import org.jspecify.annotations.Nullable;

public interface CustomerService {
    Status registerCustomer(RegistrationDto customerRegistrationDto);
    CustomerProductResponse findAllProducts();
    CustomerProductResponse findProductsByCostRange(Integer min, Integer max);
    CustomerProductResponse getProductsByCategory(ProductCategory category);
    CustomerProfileDto getCustomerProfile();
    CustomerProductDto getProductById(Long productId);
    Status addProductInWishlist(Long productId);
    CustomerProductResponse getWishlistProducts();
    Status removeProductFromWishlist(Long productId);
    Status updateCartItem(CartRequestDto cartRequestDto);
    CartResponseDto viewCart();
    Status checkoutOrder();
    OrdersDto showAllOrders();
}
