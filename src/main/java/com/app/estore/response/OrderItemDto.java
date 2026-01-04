package com.app.estore.response;

import com.app.estore.utility.ProductCategory;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;
    private Long vendorId;
    private String title;
    private String description;
    private String weight;
    private String dimensions;
    private Integer cost;
    private ProductCategory productCategory;
    private Integer productQuantity;
    private Integer priceAtPurchase;
    private Integer itemTotalCost;
}
