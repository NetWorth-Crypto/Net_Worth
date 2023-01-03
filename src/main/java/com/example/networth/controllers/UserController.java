package com.example.networth.controllers;

import com.example.networth.models.*;
import com.example.networth.repositories.FollowerRepository;
import com.example.networth.repositories.FollowingRepository;
import com.example.networth.repositories.UserRepository;
import com.example.networth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;



@Controller
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private FollowerRepository followerDao;

    @Autowired
    private FollowingRepository followingDao;

    private final UserRepository userDao;



    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userDao) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }


    @GetMapping("/sign-up")
    public String showSignupForm(Model model ){
        model.addAttribute("user", new User());

        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, RedirectAttributes attributes){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userService.saveUser(user);
attributes.addFlashAttribute("success","You successfully registered! You can now login");
        return "redirect:/login";
    }


    @GetMapping("searchUser")
    public String search(Model model, String user) {
        System.out.println(user);

        List<User> lists = userService.getByUser(user);
        model.addAttribute("lists", lists);
        System.out.println(lists);
        return "users/searchUser";
    }

//    @GetMapping("searchFollower")
//    public String searchFollower(Model model, String user) {
//        System.out.println(user);
//        List<User> lists = userService.getByUser(user);
//        model.addAttribute("lists", lists);
//        System.out.println(lists);
//        return "users/followers";
//    }

//    @GetMapping("searchFollowing")
//    public String searchFollowing(Model model, String user) {
//        System.out.println(user);
//        List<User> lists = userService.getByUser(user);
//        model.addAttribute("lists", lists);
//        System.out.println(lists);
//        return "users/following";
//    }





//    @GetMapping("/followers")
//    public String testFollower(Model model) {
//        model.addAttribute("follower", new Follower());
//        return "users/followers";
//    }

//    @PostMapping("/create/followers")
//    public String testFollower1(@ModelAttribute("follower") Follower follower,
//                                @RequestParam("userId") long follower_user_id) {
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

    @PostMapping("/following/user")
    public String followingUser(@RequestParam("userId") long userId){

        System.out.println(userId);
        //Get followings from database
        Following following = followingDao.getReferenceById(userId);

        /*Change this to the actual logged in user*/
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        //Get all followed users
        List<Following> userFollowing = following.getFollowingList();

//        Check user already followed the user
        for(Following following1: userFollowing){
            if(following1.getId() == following.getId()){

                //remove from following table
                user.removeFollowing(following1);
                following.removeFollowing(following1);
                followingDao.delete(following1);
                System.out.println("not following anymore");
                //return to page
                return "redirect:/users#user"+userId;
            }
        }

        Following postFollowing = new Following(user,following);
        user.addFollowing(postFollowing);
        following.addFollowing(postFollowing);
        followingDao.save(postFollowing);
        System.out.println("followers added");


        return "redirect:/users#user"+userId;
    }

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



//    @GetMapping("/follow")
//    public String testFollow(Model model) {
//        model.addAttribute("following", new Following());
//        return "users/follow";
//    }

//    @PostMapping("/follow/user")
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



//    @GetMapping("/following")
//    public String testFollowing(Model model) {
//        return "users/following";
//    }

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








}





