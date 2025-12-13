package com.app.estore.request;


import lombok.Data;

@Data
public class CustomerRegistrationDto {
    private String email;
    private String password;
    private String name;
    private String phone;
}
