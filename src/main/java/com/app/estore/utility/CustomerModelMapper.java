package com.app.estore.utility;

import com.app.estore.entity.Customer;
import com.app.estore.entity.Order;
import com.app.estore.entity.OrderItem;
import com.app.estore.entity.Product;
import com.app.estore.response.CustomerProfileDto;
import com.app.estore.response.OrderDto;
import com.app.estore.response.OrderItemDto;
import com.app.estore.response.OrderResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerModelMapper {

    public CustomerProfileDto convertToCustomerDto(Customer customer) {
        CustomerProfileDto customerProfileDto = new CustomerProfileDto();
        customerProfileDto.setEmail(customer.getEmail());
        customerProfileDto.setName(customer.getName());
        customerProfileDto.setPhone(customer.getPhone());
        customerProfileDto.setCreatedAt(customer.getCreatedAt());
        return customerProfileDto;
    }

    public OrderResponseDto orderResponseDto(List<Order> orderList) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setTotalOrders(orderList.size());
        List<OrderDto> orderDtoList = new ArrayList<>();

        for(Order order : orderList) {
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderId(order.getOrderId());
            orderDto.setOrderDate(order.getOrderDate());
            orderDto.setTotalCost(order.getTotalCost());

            List<OrderItemDto> orderItemDtoList = new ArrayList<>();

            for(OrderItem orderItem : order.getItems()) {
                OrderItemDto orderItemDto = new OrderItemDto();
                Product product = orderItem.getProduct();

                orderItemDto.setProductId(product.getProductId());
                orderItemDto.setVendorId(product.getVendor().getVendorId());
                orderItemDto.setTitle(product.getTitle());
                orderItemDto.setDescription(product.getDescription());
                orderItemDto.setWeight(product.getWeight());
                orderItemDto.setDimensions(product.getDimensions());
                orderItemDto.setCost(product.getCost());
                orderItemDto.setProductCategory(product.getProductCategory());
                orderItemDto.setProductQuantity(orderItem.getProductQuantity());
                orderItemDto.setItemTotalCost(orderItem.getItemTotalCost());

                orderItemDtoList.add(orderItemDto);
            }

            orderDto.setOrderItemDtoList(orderItemDtoList);
            orderDtoList.add(orderDto);
        }

        orderResponseDto.setOrderDtoList(orderDtoList);
        return orderResponseDto;
    }
}
