package com.example.networth.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false, unique = true, name = "user_name")
    private String username;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String profilePicture = "https://cdn.filestackcontent.com/SftfgsETQmEGDT0gfjsq"; //Default image

    @Column
    private String userTitle;

    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    List<Role> roles = new ArrayList<>();



    @ManyToMany(
            )
    @JoinTable(
            name = "user_follower",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")}
    )

    private List<Follower> followers;


    @ManyToMany()
    @JoinTable(
            name = "user_following",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "following_id")}
    )
    private List<Following> followings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",orphanRemoval = true)
    private List<Portfolio> portfolios;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<PostLike> likes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<PostDislike> dislikes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments;

    //Constructors
    public User() {
    }







    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        firstName = copy.firstName;
        lastName = copy.lastName;
        email = copy.email;
        username = copy.username;
        password = copy.password;
        roles = copy.roles;
    }

    public User(long id, String firstName, String lastName, String email, String password,String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;


    }




    //Add and Remove PostLike objects
    public void addLike(PostLike postLike){
        this.likes.add(postLike);
        postLike.setUser(this);
    }

    public void removeLike(PostLike postLike){
        this.likes.remove(postLike);
        postLike.setUser(null);
    }

    //Add and Remove PostDisLike objects
    public void addDislike(PostDislike postDisLike){
        this.dislikes.add(postDisLike);
        postDisLike.setUser(this);
    }

    public void removeDislike(PostDislike postDisLike){
        this.dislikes.remove(postDisLike);
        postDisLike.setPost(null);
    }

    //Add and Remove PostLike objects
    public void addPost(Post post){
        this.posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        post.setUser(null);
    }
    //Add and remove Follower objects
    public void addFollower(Follower follower){
        this.followers.add(follower);
        follower.setUser(this);
    }

    public void removeFollower(Follower follower){
        this.followers.remove(follower);
        follower.setUser(null);
    }

    //Add and remove Following objects
    public void addFollowing(Following following){
        this.followings.add(following);
        following.setUser(this);
    }

    public void removeFollowing(Following following){
        this.followings.remove(following);
        following.setUser(null);
    }



    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}