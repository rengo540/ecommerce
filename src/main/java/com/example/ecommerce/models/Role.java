package com.example.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public  Role(String name){
        this.name = name;
    }
}
