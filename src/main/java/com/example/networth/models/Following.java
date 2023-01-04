package com.example.networth.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "following_user_id")
    Following following;

    //Constructors



    public Following(User user, Following following) {
        this.user = user;
        this.following = following;
    }

    public Following() {

    }

    public Following(User user, User userFollowings) {
    }


    public void removeFollowing(Following following1) {
    }

    public void addFollowing(Following postFollowing) {
    }

    public void setFollowing_user_id(long following_user_id) {
    }


    //    @Column(nullable = false ,unique = true)
//    private long following_user_id;
//
//    public Following() {
//    }
//
//    public Following(User user, long following_user_id) {
//        this.user = user;
//        this.following_user_id = following_user_id;
//    }
//
//    public Following(User user, Following following) {
//
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public long getFollowing_user_id() {
//        return following_user_id;
//    }
//
//    public void setFollowing_user_id(long following_user_id) {
//        this.following_user_id = following_user_id;
//    }
//
//    public void removeFollowing(Following following1) {
//    }
//
//    public void addFollowing(Following postFollowing) {
//    }
}
