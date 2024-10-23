package com.example.ecommerce.dtos;

import com.example.ecommerce.models.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.sql.Blob;

@Data
public class ImageDto {
    private Long id ;
    private  String fileName ;
    private String fileType;
    private String imagePath;

}

