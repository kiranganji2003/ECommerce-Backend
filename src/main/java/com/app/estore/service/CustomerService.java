package com.app.estore.service;

import com.app.estore.request.CustomerRegistrationRequest;
import com.app.estore.response.Status;

public interface CustomerService {
    Status registerCustomer(CustomerRegistrationRequest registerCustomerRequest);
}
