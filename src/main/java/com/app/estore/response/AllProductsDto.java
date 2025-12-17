package com.app.estore.response;

import com.app.estore.common.ProductCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AllProductsDto {
    private Integer productId;
    private Integer vendorId;
    private String title;
    private String description;
    private String weight;
    private String dimensions;
    private Integer cost;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
}
