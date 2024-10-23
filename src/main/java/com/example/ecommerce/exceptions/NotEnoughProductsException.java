package com.example.ecommerce.exceptions;

public class NotEnoughProductsException extends RuntimeException{
    public NotEnoughProductsException(String message){
        super(message);
    }

}
