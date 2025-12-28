package com.app.estore.entity;

import com.app.estore.utility.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (Hibernate.getClass(this) != Hibernate.getClass(o)) return false;

        Product product = (Product) o;
        return productId != null && productId.equals(product.productId);
    }

    @Override
    public int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }
}
