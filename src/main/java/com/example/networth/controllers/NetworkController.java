//package com.example.networth.controllers;
//
//import com.example.networth.models.*;
//import com.example.networth.repositories.FollowerRepository;
//import com.example.networth.repositories.FollowingRepository;
//import com.example.networth.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//
//@Controller
//public class NetworkController {
//
//    @Autowired
//    private FollowerRepository followerDao;
//
//    @Autowired
//    private FollowingRepository followingDao;
//
//    @Autowired
//    private UserRepository userDao;
//
//
//
//    @GetMapping("/followers")
//    public String testFollower(Model model) {
//        model.addAttribute("follower", new Follower());
//        return "users/followers";
//    }
//
//    @PostMapping("/create/followers")
//    public String testFollower1(@ModelAttribute("follower") Follower follower,
//                                @RequestParam("follower_user_id") long follower_user_id) {
//        //Get UserId from logged-in user to create new follower
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        follower.setUser(user);
//        follower.setFollower_user_id(follower_user_id);
//        //Save new follower to database
//
//        //System.out.println(follower.getFollower_user_id());
//
//        followerDao.save(follower);
//        return "users/followers";
//    }
//
//    @PostMapping("/users/followers")
//    public String followTestUser(@ModelAttribute Follower follower) {
//
//        User userFollower = userDao.getReferenceById(follower.getFollower_user_id());
//
//        /*Change this to the actual logged in user*/
//        User user = userDao.getReferenceById(1L);
//
//        List<Follower> followers = user.getFollowers();
//
//        //Check user already followed the user
//        for (Follower follower1 : followers) {
//            if (follower1.getFollower_user_id() == userFollower.getId()) {
//
//                //remove from PostLike table
//                user.addFollower(follower1);
//                userFollower.removeFollower(follower1);
//                followerDao.delete(follower1);
//                System.out.println("no more follwing " + follower1.getUser());
//                //return to page
//                return "users/followers";
//            }
//        }
//        return "users/followers";
//    }
//
//
//
//
//    @GetMapping("/follow")
//    public String testFollow(Model model) {
//        model.addAttribute("following", new Following());
//        return "users/follow";
//    }
//
//    @PostMapping("/following/users")
//    public String followingTestUser(@ModelAttribute Following following) {
//
//        User userFollowing = userDao.getReferenceById(following.getFollowing_user_id());
//
//        /*Change this to the actual logged in user*/
//        User user = userDao.getReferenceById(1L);
//
//        List<Following> followings = user.getFollowings();
//
//        //Check user already following the user
//        for (Following following1 : followings) {
//            if (following1.getFollowing_user_id() == userFollowing.getId()) {
//
//                //remove from follower from table
//                user.addFollowing(following1);
//                userFollowing.removeFollowing(following1);
//                followingDao.delete(following1);
//                System.out.println("You are following " + following1.getUser());
//                //return to page
//                return "users/following";
//            }
//        }
//        return "users/following";
//    }
//
//
//
//    @GetMapping("/following")
//    public String testFollowing(Model model) {
//        return "users/following";
//    }
//
//    @PostMapping("/users/following")
//    public String testFollowing(@ModelAttribute("following") Following following, @RequestParam("following_user_id") long following_user_id) {
//        //Get UserId from logged-in user to create following
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        //Create new following
//        following.setUser(user);
//        following.setFollowing_user_id(following_user_id);
//        //Save new following to database
//
//        //        System.out.println(following.getFollowing_user_id());
//
//        followingDao.save(following);
//        return "users/following";
//    }
//
//
//
//
//
//}
//
