package com.example.networth.repositories;


import com.example.networth.models.Post;
import com.example.networth.models.PostLike;
import com.example.networth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    public PostLike findPostLikeByPost(Post post);
    public PostLike findPostLikeByPostAndUser(Post post, User user);
}
