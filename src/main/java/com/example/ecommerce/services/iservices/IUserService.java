package com.example.ecommerce.services.iservices;

import com.example.ecommerce.dtos.CreateUserRequest;
import com.example.ecommerce.dtos.UserDto;
import com.example.ecommerce.dtos.UserUpdateRequest;
import com.example.ecommerce.models.User;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request,Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
