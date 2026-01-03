package com.app.estore.request;

import lombok.Data;

@Data
public class CartRequestDto {
    private Long productId;
    private Integer productQuantity;
}
