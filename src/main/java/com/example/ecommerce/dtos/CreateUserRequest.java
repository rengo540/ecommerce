package com.example.ecommerce.dtos;

import lombok.Data;

@Data
public class    CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
