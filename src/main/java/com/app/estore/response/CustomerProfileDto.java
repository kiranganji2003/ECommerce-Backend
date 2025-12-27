package com.app.estore.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerProfileDto {
    private String email;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
}
