package com.app.estore.service;

import com.app.estore.request.ProductRequestDto;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.Status;
import com.app.estore.response.VendorProfileDto;

public interface VendorService {
    Status registerVendor(RegistrationDto registrationDto);
    Status addProduct(ProductRequestDto productRequestDto);
    VendorProfileDto getVendorProfile();
    Status deleteProductById(Long productId);
}
