package com.example.networth.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;


    @Column(nullable = false,unique = true)
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> users;


    //Constructors
    public Role() {
    }

    public Role(long role_id, String type) {
        this.role_id = role_id;
        this.type = type;
    }

    public Role(long role_id, String type, List<User> users) {
        this.role_id = role_id;
        this.type = type;
        this.users = users;
    }

    //Getters and Setters
    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
