package com.app.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer vendorId;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String phone;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "vendor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Product> productList;
}
