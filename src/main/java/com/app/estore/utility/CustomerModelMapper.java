package com.app.estore.utility;

import com.app.estore.entity.Customer;
import com.app.estore.response.CustomerProfileDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerModelMapper {

    public CustomerProfileDto convertToCustomerDto(Customer customer) {
        CustomerProfileDto customerProfileDto = new CustomerProfileDto();
        customerProfileDto.setEmail(customer.getEmail());
        customerProfileDto.setName(customer.getName());
        customerProfileDto.setPhone(customer.getPhone());
        customerProfileDto.setCreatedAt(customer.getCreatedAt());
        return customerProfileDto;
    }

}
