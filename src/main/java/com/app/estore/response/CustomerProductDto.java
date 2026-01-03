package com.app.estore.response;

import com.app.estore.utility.ProductCategory;
import lombok.Data;

@Data
public class CustomerProductDto {
    private Integer productId;
    private Integer vendorId;
    private String title;
    private String description;
    private String weight;
    private String dimensions;
    private Integer cost;
    private ProductCategory productCategory;
}
