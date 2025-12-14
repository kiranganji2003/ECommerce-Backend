package com.app.estore.request;

import lombok.Data;

@Data
public class ProductRequestDto {
    private String title;
    private String description;
    private String weight;
    private String dimensions;
    private Integer cost;
}
