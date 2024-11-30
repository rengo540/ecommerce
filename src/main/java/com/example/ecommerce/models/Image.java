package com.example.ecommerce.models;

import com.example.ecommerce.models.auditing.BaseEntityAuditing;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "imagePath" }))
public class Image extends BaseEntityAuditing {

    private  String fileName ;
    private String fileType;
    private String imagePath;

    @ManyToOne()
    @JoinColumn(name="product_id")
    private Product product;


    public Image(){}

}
