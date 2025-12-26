package com.app.estore.entity;

import com.app.estore.utility.ProductCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AllProducts {
    @Id
    private Integer productId;
    private Integer vendorId;
    private Integer cost;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
}
