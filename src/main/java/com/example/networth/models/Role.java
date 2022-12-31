package com.example.networth.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false,unique = true)
    private String type;

    @Column
  private String  details;




    //Constructors
    public Role() {
    }

    public Role(long id, String type, String details) {
        this.id = id;
        this.type = type;
        this.details = details;
    }

    public Role(String details, String type) {
        this.details = details;
        this.type = type;

    }

    //Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long role_id) {
        this.id = role_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
