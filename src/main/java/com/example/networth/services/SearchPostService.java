package com.example.networth.services;

import com.example.networth.models.Post;
import com.example.networth.repositories.PostRepository;
import com.example.networth.repositories.SearchPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchPostService {

    @Autowired
    private SearchPostRepository searchPostRepository;

//    /*
//      Get the List of Post
//     */
//    public List<Post> getAllPost(){
//        List<Post> list = ( List<Post>)searchPostRepository.findAll();
//        return list;
//    }

    /*
     * get post by keyword
     */
    public List<Post> getByKeyword(String keyword){
        return searchPostRepository.findByKeyword(keyword);
    }
}
