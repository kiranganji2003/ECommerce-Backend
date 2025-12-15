package com.app.estore.entity;

import com.app.estore.common.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String title;
    private String description;
    private String weight;
    private String dimensions;
    private Integer cost;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Vendor vendor;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
