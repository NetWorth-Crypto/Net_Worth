package com.example.networth.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
//import org
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name ="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id")
    private User user;


    @Column
    private String imgUrl;

    @Column
    private String videoUrl;

    @Column(length = 1000)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post",orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private List<PostLike> likes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private List<PostDislike> dislikes;

    //Constructors
    public Post() {
    }

    public Post(String imgUrl, String videoUrl,String description) {

        this.imgUrl = imgUrl;
        this.description = description;
        this.videoUrl = videoUrl;
    }

    public Post(long id, User user, String imgUrl, String videoUrl, String description, List<Comment> comments, List<PostLike> likes, List<PostDislike> dislikes) {
        this.id = id;
        this.user = user;
        this.imgUrl = imgUrl;
        this.videoUrl = videoUrl;
        this.description = description;
        this.comments = comments;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    //Add and Remove PostLike objects
    public void addLike(PostLike postLike){
        this.likes.add(postLike);
        postLike.setPost(this);
    }

    public void removeLike(PostLike postLike){
        this.likes.remove(postLike);
        postLike.setPost(null);
    }

    //Add and Remove PostDisLike objects
    public void addDislike(PostDislike postDisLike){
        this.dislikes.add(postDisLike);
        postDisLike.setPost(this);
    }

    public void removeDislike(PostDislike postDisLike){
        this.dislikes.remove(postDisLike);
        postDisLike.setPost(null);
    }



}
