package com.app.estore.controller;

import com.app.estore.entity.Customer;
import com.app.estore.repository.CustomerRepository;
import com.app.estore.request.LoginRequest;
import com.app.estore.response.JwtResponse;
import com.app.estore.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerRepository customerRepository;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {


        Optional<Customer> customer = customerRepository.findByEmail(request.getEmail());

                System.out.println(
                customer.get().getName()
                        );

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token = jwtTokenProvider.generateToken(auth);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse("Invalid credentials"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JwtResponse("Auth error"));
        }

//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//
//        String token = jwtTokenProvider.generateToken(auth);
//        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/register")
    public String hello() {
        return "Greeting hello";
    }
}
