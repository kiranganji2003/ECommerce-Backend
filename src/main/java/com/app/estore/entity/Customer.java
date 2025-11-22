package com.app.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private Long phone;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
