package com.app.estore.service;

import com.app.estore.dto.CustomerDto;
import com.app.estore.dto.Status;

public interface CustomerService {
    Status registerCustomer(CustomerDto customerDto);
}
