package com.example.ecommerce.security;

import com.example.ecommerce.models.User;
import com.example.ecommerce.repos.UserRepo;
import com.example.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =userRepo.findByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException(" user not found")
        );
        return UserAppDetails.buildUserDetails(user);
    }
}
