package com.example.networth.repositories;

import com.example.networth.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchPostRepository extends JpaRepository<Post,Long> {

    @Query(value = "select * from post where post.title like '%:keyword%' or post.description like '%:keyword% 'or post.user_id like '%:keyword%' ", nativeQuery = true)
    List<Post>findByKeyword(@Param("keyword") String keyword);

}
