package com.example.ecommerce.models;

import com.example.ecommerce.models.auditing.BaseEntityAuditing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
public class Role extends BaseEntityAuditing {


    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public  Role(String name){
        this.name = name;
    }
}
