package com.app.estore.controller;

import com.app.estore.repository.CustomerRepository;
import com.app.estore.request.LoginRequest;
import com.app.estore.response.JwtResponse;
import com.app.estore.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
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

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtTokenProvider.generateToken(auth);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/register")
    public String hello() {
        return "Greeting hello";
    }
}
