package com.example.networth.controllers;


import com.example.networth.models.*;
import com.example.networth.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostRepository postDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PostLikeRepository postLikeDao;

    @Autowired
    private PostDislikeRepository postDislikeDao;

    @Autowired
    private CommentRepository commentDao;


    /********************COMPLETE ROUTES********************/
    @GetMapping("/posts")
    public String userPost(Model model){
        //Get All Post
        List<Post> posts = postDao.findAll();
        Collections.reverse(posts);

        List<Comment> comments = commentDao.findAll();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("user",user);
        model.addAttribute("posts",posts);
        model.addAttribute("comments",comments);
        model.addAttribute("newPost",new Post());
        model.addAttribute("newComment", new Comment());

        System.out.println(posts.get(0).getVideoUrl());
        System.out.println(posts.get(1).getVideoUrl());
        System.out.println(posts.get(2).getVideoUrl());

        return "feed";
    }


    @PostMapping("/posts/create")
    public String testPost1(@ModelAttribute("newPost") Post newPost,
                            @RequestParam("imgUrl") String imgUrl,
                            @RequestParam("videoUrl") String videoUrl){
        //Get UserId from logged-in user to create new post
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        //Save image/video to new post
        newPost.setImgUrl(imgUrl);
        newPost.setVideoUrl(videoUrl);

        //Check if user logged-in
        System.out.println(user.getId());

        //Save new post to database
        user.getPosts().add(newPost);
        newPost.setUser(user);
        postDao.save(newPost);
        return "redirect:/posts";
    }

    @PostMapping("/like/post")
    public String likePost(@RequestParam("postId") long postId){

        System.out.println(postId);
        //Get liked post from database
        Post likedPost = postDao.getReferenceById(postId);

        /*Change this to the actual logged in user*/
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        //Get all users' likes
        List<PostLike> userLikes = user.getLikes();

        //Check user already like the post
        for(PostLike like: userLikes){
            if(like.getPost().getId() == likedPost.getId()){

                //remove from PostLike table
                user.removeLike(like);
                likedPost.removeLike(like);
                postLikeDao.delete(like);
                System.out.println("like removed");
                //return to page
                return "redirect:/posts#post"+postId;
            }
        }

        PostLike postLike = new PostLike(user,likedPost);
        user.addLike(postLike);
        likedPost.addLike(postLike);
        postLikeDao.save(postLike);
        System.out.println("like added");


        return "redirect:/posts#post"+postId;
    }

    @PostMapping("/dislike/post")
    public String dislikePost(@RequestParam("postId") long postId){

        Post dislikedPost = postDao.getReferenceById(postId);

        /*Change this to the actual logged in user*/
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        List<PostDislike> userDislikes = user.getDislikes();

        //Check user already like the post
        for(PostDislike dislike: userDislikes){
            if(dislike.getPost().getId() == dislikedPost.getId()){

                //remove from PostDislike table
                user.removeDislike(dislike);
                dislikedPost.removeDislike(dislike);
                postDislikeDao.delete(dislike);
                System.out.println("dislike removed");
                //return to page
                return "redirect:/posts#post"+postId;
            }
        }

        PostDislike postDislike = new PostDislike(user,dislikedPost);
        user.addDislike(postDislike);
        dislikedPost.addDislike(postDislike);
        postDislikeDao.save(postDislike);
        System.out.println("dislike added");

        return "redirect:/posts#post"+postId;
    }

    @PostMapping("/posts/{id}/create/comment")
    public String createComment(@ModelAttribute Comment comment,
                                @PathVariable long id) {

        EntityManager em;
        System.out.println("Comment Section Hit!");
        //find user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());
        System.out.println(user.getFirstName());

        //find post
        Post post = postDao.getReferenceById(id);
        System.out.println(post.getDescription());




        //add comment to post comment list
        post.getComments().add(comment);
        //add comment to user comment list
        user.getComments().add(comment);
        //Save to database
        System.out.println("Comments added to entities");

        //set comment fields(post,user)
        comment.setPost(post);
        comment.setUser(user);
        commentDao.save(comment);

        return "redirect:/posts#post"+id;
    }

    /********************TEST ROUTES********************/
    @GetMapping("/createpost")
    public String testPost(Model model){
        model.addAttribute("post", new Post());
        return "TestTemplates/CreatePost";
    }

    @GetMapping("/search")
    public String getSearch()
    {
        return "post/search";
    }
    @PostMapping("/search")
    public String postSearch(@RequestParam(value = "searchValue")String searchValue) {
        System.out.println(searchValue);
        return "redirect:profile";
    }

}


