package com.example.ecommerce.dtos;

import com.example.ecommerce.models.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private String name ;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}