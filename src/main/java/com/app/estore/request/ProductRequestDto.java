package com.app.estore.request;

import com.app.estore.common.ProductCategory;
import lombok.Data;

@Data
public class ProductRequestDto {
    private String title;
    private String description;
    private String weight;
    private String dimensions;
    private Integer cost;
    private ProductCategory productCategory;
}
