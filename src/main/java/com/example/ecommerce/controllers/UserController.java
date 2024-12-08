package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.CreateUserRequest;
import com.example.ecommerce.dtos.UserDto;
import com.example.ecommerce.dtos.UserUpdateRequest;
import com.example.ecommerce.exceptions.AlreadyExistsException;
import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.User;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));

    }
    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));

    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));

    }

}
