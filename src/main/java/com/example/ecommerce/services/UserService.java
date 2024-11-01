package com.example.ecommerce.services;

import com.example.ecommerce.dtos.CreateUserRequest;
import com.example.ecommerce.dtos.UserDto;
import com.example.ecommerce.dtos.UserUpdateRequest;
import com.example.ecommerce.exceptions.AlreadyExistsException;
import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repos.UserRepo;
import com.example.ecommerce.security.UserAppDetails;
import com.example.ecommerce.services.iservices.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService  {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user->!userRepo.existsByEmail(request.getEmail()))
                .map(us-> {
                    User user =new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepo.save(user);
                }).orElseThrow(()->new AlreadyExistsException("the user already exist"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepo.findById(userId)
                .map(user -> {
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepo.save(user);
                }).orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,
                ()->{throw new ResourceNotFoundException("user not exist");
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAppDetails userD= (UserAppDetails) authentication.getPrincipal();
        return userRepo.findByEmail(userD.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
    }


}
