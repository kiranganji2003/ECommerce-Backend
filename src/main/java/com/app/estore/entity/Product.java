package com.app.estore.entity;

import com.app.estore.utility.ProductCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
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
