package com.app.estore.controller;

import com.app.estore.response.*;
import com.app.estore.utility.ProductCategory;
import com.app.estore.request.LoginRequest;
import com.app.estore.request.RegistrationDto;
import com.app.estore.security.JwtTokenProvider;
import com.app.estore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtTokenProvider.generateToken(auth);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Status> registerCustomer(@RequestBody RegistrationDto customerRegistrationDto) {
        return ResponseEntity.ok(customerService.registerCustomer(customerRegistrationDto));
    }

    @GetMapping("/products")
    public ResponseEntity<CustomerProductResponse> listAllProducts() {
        return ResponseEntity.ok(customerService.findAllProducts());
    }

    @GetMapping("/products/cost")
    public ResponseEntity<CustomerProductResponse> listAllProductsByCost(@RequestParam Integer min,
                                                                         @RequestParam Integer max) {
        return ResponseEntity.ok(customerService.findProductsByCostRange(min, max));
    }

    @GetMapping("/products/category")
    public ResponseEntity<CustomerProductResponse> getProductsByCategory(@RequestParam ProductCategory category) {
        return ResponseEntity.ok(customerService.getProductsByCategory(category));
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerProfileDto> getCustomerProfile() {
        return ResponseEntity.ok(customerService.getCustomerProfile());
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<CustomerProductDto> getProductById(@PathVariable Integer productId) {
        return ResponseEntity.ok(customerService.getProductById(productId));
    }

    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<Status> addProductInWishlist(@PathVariable Integer productId) {
        return ResponseEntity.ok(customerService.addProductInWishlist(productId));
    }
}
