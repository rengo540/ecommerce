package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.repos.CartItemRepo;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repos.CartRepo;
import com.example.ecommerce.services.iservices.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartItemService implements ICartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepo cartRepo;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //get the cart
        Cart cart = cartService.getCart(cartId);
        //get the product
        Product product = productService.getProductById(productId);
        //check if the product is already in the cart
        //if yes ,then increase the quantity
        //if no ,initiate a new cartItem and save it
        CartItem cartItem= cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());
        if (cartItem.getId() ==null){
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(quantity+cartItem.getQuantity());
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem); //update totalAmount inside this.
        cartItemRepo.save((cartItem));
        cartRepo.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart =cartService.getCart(cartId);
        CartItem cartItem = cart.getItems().stream()
                .filter(item ->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new ResourceNotFoundException("this product is not in cart"));
        cart.removeItem(cartItem);
        cartRepo.save(cart);
        cartItemRepo.delete(cartItem);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int newQuantity) {
        //get the cart
        Cart cart = cartService.getCart(cartId);
        //get the product
        Product product = productService.getProductById(productId);
        cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item ->{
                    item.setQuantity(newQuantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                    cartItemRepo.save(item);
                });
        BigDecimal totalAmount =cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepo.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()->new ResourceNotFoundException("item not found"));
    }
}
