package com.app.estore.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListProductResponse {
    private List<AllProductsDto> productsList;
}
