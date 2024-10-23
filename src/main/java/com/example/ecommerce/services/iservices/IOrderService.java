package com.example.ecommerce.services.iservices;

import com.example.ecommerce.dtos.OrderDto;
import com.example.ecommerce.models.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
