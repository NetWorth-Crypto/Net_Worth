package com.example.networth.services;

import com.example.networth.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service("postService")
public class PostService
{
    private final PostRepository postDao;

    public PostService(PostRepository)
    {
        this.postDao = postDao;
    }
}
