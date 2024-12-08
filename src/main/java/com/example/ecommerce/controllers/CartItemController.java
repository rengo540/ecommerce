package com.example.ecommerce.controllers;

import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.User;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.services.iservices.ICartItemService;
import com.example.ecommerce.services.iservices.ICartService;
import com.example.ecommerce.services.iservices.IUserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    @Autowired
    private ICartItemService cartItemService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IUserService userService;
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                    @RequestParam Integer quantity){

            Long cart_id=-1L;
         if(cartId==null){
             User user = userService.getAuthenticatedUser();
             cart_id= cartService.intializrCart(user);
         }


            cartItemService.addItemToCart(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("cartId",cart_id));

    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long productId){

            cartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(new ApiResponse("remove item success",null));

    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer newQuantity){

            cartItemService.updateItemQuantity(cartId,productId,newQuantity);
            return ResponseEntity.ok(new ApiResponse("update item quantity success",null));

    }
}
