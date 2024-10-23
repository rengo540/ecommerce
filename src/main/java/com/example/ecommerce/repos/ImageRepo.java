package com.example.ecommerce.repos;

import com.example.ecommerce.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long productId);

    Image findByFileName(String fileName);
    Image findFirstByProductId(Long productId);
}
