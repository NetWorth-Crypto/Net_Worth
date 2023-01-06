//package com.example.networth.models;
//
//import javax.persistence.*;
//
//@Entity
//public class Following {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
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
//}

package com.example.networth.models;

import javax.persistence.*;

@Entity
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false ,unique = true)
    private long following_user_id;

    public Following() {
    }

    public Following(User user, long following_user_id) {
        this.user = user;
        this.following_user_id = following_user_id;
    }

    public Following(User user, Following following) {

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

    public long getFollowing_user_id() {
        return following_user_id;
    }

    public void setFollowing_user_id(long following_user_id) {
        this.following_user_id = following_user_id;
    }
}