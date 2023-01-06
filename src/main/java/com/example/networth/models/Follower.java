package com.example.networth.models;


import javax.persistence.*;
@Entity
public class Follower{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(nullable = false,unique = true)
    private long follower_user_id;

    public Follower() {
    }

    public Follower(User user, long follower_user_id) {
        this.user = user;
        this.follower_user_id = follower_user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getFollower_user_id() {
        return follower_user_id;
    }

    public void setFollower_user_id(long follower_user_id) {
        this.follower_user_id = follower_user_id;
    }
}
