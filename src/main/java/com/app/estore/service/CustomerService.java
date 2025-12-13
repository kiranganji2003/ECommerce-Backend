package com.app.estore.service;

import com.app.estore.request.CustomerRegistrationDto;
import com.app.estore.response.Status;

public interface CustomerService {
    Status registerCustomer(CustomerRegistrationDto customerRegistrationDto);
}
