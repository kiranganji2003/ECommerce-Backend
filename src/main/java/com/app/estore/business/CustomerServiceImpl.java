package com.app.estore.business;

import com.app.estore.entity.Customer;
import com.app.estore.repository.CustomerRepository;
import com.app.estore.request.CustomerRegistrationDto;
import com.app.estore.response.Status;
import com.app.estore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Status registerCustomer(CustomerRegistrationDto customerRegistrationDto) {
        System.out.println(customerRegistrationDto);
        Customer customer = new Customer();
        customer.setEmail(customerRegistrationDto.getEmail());
        customer.setPassword(customerRegistrationDto.getPassword());
        customer.setName(customerRegistrationDto.getName());
        customer.setPhone(customerRegistrationDto.getPhone());
        customerRepository.save(customer);
        return new Status("Registered Successfully");
    }
}
