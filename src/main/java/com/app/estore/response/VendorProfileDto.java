package com.app.estore.response;

import com.app.estore.entity.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VendorProfileDto {
    private String email;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private List<Product> productList;
}
