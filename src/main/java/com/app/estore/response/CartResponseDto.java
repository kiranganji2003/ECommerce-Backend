package com.app.estore.response;

import com.app.estore.utility.ProductCategory;
import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {

    private List<CartProduct> cartProductList;
    private Integer cartTotalCost;

    @Data
    public static class CartProduct {
        private Long productId;
        private Long vendorId;
        private String title;
        private String description;
        private String weight;
        private String dimensions;
        private Integer cost;
        private ProductCategory productCategory;
        private Integer productQuantity;
        private Integer totalCost;
    }

}
