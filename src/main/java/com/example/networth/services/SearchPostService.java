package com.example.networth.services;

import com.example.networth.models.Follower;
import com.example.networth.models.Following;
import com.example.networth.models.Post;
import com.example.networth.repositories.FollowerRepository;
//import com.example.networth.repositories.FollowingRepository;
import com.example.networth.repositories.FollowingRepository;
import com.example.networth.repositories.SearchPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchPostService{
    @Autowired
    private SearchPostRepository searchPostRepository;

    private FollowingRepository followingRepository;

    private FollowerRepository followerRepository;

    //creating a list of a post by keyword.
    public List<Post> getByKeyword(String keyword){
        List<Post> lists =  (List<Post>)searchPostRepository.findByKeyword(keyword);
        return lists;
    }

    //creating a list of a follower by keyword.

//    public List<Follower> getByFollowerName(String keyword){
//        List<Follower> lists =  (List<Follower>)followerRepository.findByKeyword(keyword);
//        return lists;
//    }

    //creating a list of a following by keyword.

//    public List<Following> getByFollowingName(String keyword){
//        List<Following> lists =  (List<Following>)followingRepository.findByKeyword(keyword);
//        return lists;
//    }




}