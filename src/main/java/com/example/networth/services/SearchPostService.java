package com.example.networth.services;

import com.example.networth.models.Post;
import com.example.networth.repositories.SearchPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchPostService{
    @Autowired
    private SearchPostRepository searchPostRepository;
    //creating a list of a post by keyword.
    public List<Post> getByKeyword(String keyword){
        List<Post> lists =  (List<Post>)searchPostRepository.findByKeyword(keyword);
        return lists;
    }
}