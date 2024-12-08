package com.example.ecommerce.controllers;

import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.services.iservices.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){

           Cart cart = cartService.getCart(cartId);
           return ResponseEntity.ok(new ApiResponse("success",cart));

    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){

            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("clear success",null));

    }

    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable  Long cartId){

            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return  ResponseEntity.ok(new ApiResponse("total price",totalPrice));

    }
}
