package com.example.ecommerce.repos;

import com.example.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
