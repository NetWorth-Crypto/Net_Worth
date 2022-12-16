package com.example.networth.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 2500)
    String imgUrl;

    @Column(nullable = false, length = 3000)
    String message;

    public Comment() {
    }

    public Comment(long id, Post post, User user, String imgUrl, String message) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.imgUrl = imgUrl;
        this.message = message;
    }


}
