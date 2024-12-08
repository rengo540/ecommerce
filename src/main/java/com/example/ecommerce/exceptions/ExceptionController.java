package com.example.ecommerce.exceptions;

import com.example.ecommerce.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(ResourceNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "resource not found in the system"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "this product not found in the system"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "this item already exist in the system"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(NotEnoughProductsException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughProductsException(NotEnoughProductsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Not enough products in the system"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughProductsException(JwtException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "unauthorize user"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughProductsException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "An unexpected error occurred"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
