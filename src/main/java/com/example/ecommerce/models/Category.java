package com.example.ecommerce.models;

import com.example.ecommerce.models.auditing.BaseEntityAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Category extends BaseEntityAuditing {

    private String name ;


    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> product ;

    public Category(String name) {
        this.name=name;
    }
}
