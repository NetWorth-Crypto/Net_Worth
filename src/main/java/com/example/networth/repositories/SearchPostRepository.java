package com.example.networth.repositories;

import com.example.networth.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SearchPostRepository extends JpaRepository<Post,String>{
    //query to search for keywords through post title and description.
    @Query(value = "SELECT * FROM post p WHERE p.title LIKE %:keyword% OR p.description LIKE %:keyword%",nativeQuery = true)
    List<Post> findByKeyword(@Param("keyword") String keyword);
}