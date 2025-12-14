package com.app.estore.request;


import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String password;
    private String name;
    private String phone;
}
