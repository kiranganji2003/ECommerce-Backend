package com.app.estore.security;

import com.app.estore.entity.Customer;
import com.app.estore.entity.Vendor;
import com.app.estore.repository.CustomerRepository;
import com.app.estore.repository.VendorRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

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

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        String path = request.getRequestURI();

        if (path.startsWith("/customer") && customerRepo.findByEmail(username).isPresent()) {
            Customer c = customerRepo.findByEmail(username).get();
            List<GrantedAuthority> auth = List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            return new CustomUserDetails(c.getEmail(), c.getPassword(), auth);
        }

        if (path.startsWith("/vendor") && vendorRepo.findByEmail(username).isPresent()) {
            Vendor v = vendorRepo.findByEmail(username).get();
            List<GrantedAuthority> auth = List.of(new SimpleGrantedAuthority("ROLE_VENDOR"));
            return new CustomUserDetails(v.getEmail(), v.getPassword(), auth);
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}

