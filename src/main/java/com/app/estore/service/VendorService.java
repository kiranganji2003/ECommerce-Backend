package com.app.estore.service;

import com.app.estore.request.RegistrationDto;
import com.app.estore.response.Status;

public interface VendorService {
    Status registerVendor(RegistrationDto registrationDto);
}
