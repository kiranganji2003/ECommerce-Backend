package com.app.estore.security;

import com.app.estore.entity.Customer;
import com.app.estore.entity.Vendor;
import com.app.estore.repository.CustomerRepository;
import com.app.estore.repository.VendorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepo;
    private final VendorRepository vendorRepo;

    public CustomUserDetailsService(CustomerRepository customerRepo, VendorRepository vendorRepo) {
        this.customerRepo = customerRepo;
        this.vendorRepo = vendorRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Look up in customers
        Optional<Customer> customerOpt = customerRepo.findByEmail(username);
        if (customerOpt.isPresent()) {
            Customer c = customerOpt.get();
            List<GrantedAuthority> auth = List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            return new CustomUserDetails(c.getEmail(), c.getPassword(), auth);
        }

        // Look up in vendors
        Optional<Vendor> vendorOpt = vendorRepo.findByEmail(username);
        if (vendorOpt.isPresent()) {
            Vendor v = vendorOpt.get();
            List<GrantedAuthority> auth = List.of(new SimpleGrantedAuthority("ROLE_VENDOR"));
            return new CustomUserDetails(v.getEmail(), v.getPassword(), auth);
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}

