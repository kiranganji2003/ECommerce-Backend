package com.app.estore.response;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDto {
    private Integer totalOrders;
    private List<OrderDto> orderDtoList;
}
