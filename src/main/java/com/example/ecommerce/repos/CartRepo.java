package com.example.ecommerce.repos;

import com.example.ecommerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);
}
