package com.app.estore.request;

import lombok.Data;

@Data
public class CartRequestDto {
    private Integer productId;
    private Integer productQuantity;
}
