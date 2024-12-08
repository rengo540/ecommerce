package com.example.ecommerce.controllers;


import com.example.ecommerce.dtos.OrderDto;
import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.Order;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    @Autowired
    private OrderService orderService ;

    @PostMapping("/user/place-order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
            Order order =  orderService.placeOrder(userId);
            OrderDto orderDto =  orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Items Order Success!", orderDto));

    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {

            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));

    }

    @GetMapping("/user/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));

    }

}
