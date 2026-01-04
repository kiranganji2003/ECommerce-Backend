package com.app.estore.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private Integer totalCost;
    private List<OrderItemDto> orderItemDtoList;
}
