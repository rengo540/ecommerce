package com.example.ecommerce.repos;

import com.example.ecommerce.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
