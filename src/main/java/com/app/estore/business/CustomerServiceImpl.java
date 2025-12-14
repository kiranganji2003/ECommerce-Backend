package com.app.estore.business;

import com.app.estore.entity.Customer;
import com.app.estore.repository.CustomerRepository;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.Status;
import com.app.estore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.app.estore.common.EStoreConstants.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public Status registerCustomer(RegistrationDto registrationDto) {
        Customer customer = new Customer();
        customer.setEmail(registrationDto.getEmail());
        customer.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        customer.setName(registrationDto.getName());
        customer.setPhone(registrationDto.getPhone());
        customerRepository.save(customer);
        return new Status(REGISTERED_SUCCESSFULLY);
    }
}
