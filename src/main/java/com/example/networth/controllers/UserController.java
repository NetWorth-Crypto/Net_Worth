package com.example.networth.controllers;

import com.example.networth.models.*;
import com.example.networth.repositories.FollowerRepository;
import com.example.networth.repositories.UserRepository;
import com.example.networth.services.FollowerService;
import com.example.networth.services.FollowingService;
import com.example.networth.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
public class UserController {


    private final UserService userService;


    private final FollowerService followerService;
    private final PasswordEncoder passwordEncoder;


    private FollowerRepository followerDao;


    private final FollowingService followingService;

    private UserRepository userDao;





    public UserController(UserService userService, PasswordEncoder passwordEncoder, FollowerRepository followerDao, FollowerService followerService, FollowingService followingService) {


        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.followerService = followerService;

        this.followingService = followingService;
    }



    public int getFollowerSum(User user){
      int total =  followerService.getUserFollowers(user).size();
      return total;
    }



    User loggedinUser(){
      return   (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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


    @RequestMapping(value = {"/searchUser", "/searchUser/{user}"})
    public String search(Model model,  @RequestParam("user") Optional<String> user) {

        if (user.isEmpty()){
            return "users/searchUser";
        }else {

            String userName = user.get();
            System.out.println(userName);
            List<User> lists = userService.getByUser(userName);
            System.out.println(lists.size());
            model.addAttribute("lists", lists);
            System.out.println(lists);
            return "users/searchUser";
        }

    }

    @GetMapping("/searchFollower")
    public String searchFollower(Model model, String user) {
        System.out.println(user);
        List<User> lists = userService.getByUser(user);
        model.addAttribute("lists", lists);
        System.out.println(lists);
        return "users/followers";
    }

    @GetMapping("/searchFollowing")
    public String searchFollowing(Model model, String user) {
        System.out.println(user);
        List<User> lists = userService.getByUser(user);
        model.addAttribute("lists", lists);
        System.out.println(lists);
        return "users/following";
    }





    @GetMapping("/followers")
    public String testFollower(Model model) {
        model.addAttribute("follower", new Follower());
        return "users/followers";
    }

    @PostMapping("/create/followers")
    public String testFollower1(@ModelAttribute("follower") Follower follower,
                                @RequestParam("userId") long follower_user_id) {
        //Get UserId from logged-in user to create new follower
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        follower.setUser(user);
        follower.setFollower_user_id(follower_user_id);
        //Save new follower to database

        //System.out.println(follower.getFollower_user_id());

        followerDao.save(follower);
        return "users/followers";
    }

    @PostMapping("/following/user")
    public String likePost(@RequestParam("userId") long userId,Model model){

        System.out.println(userId);

        /*Change this to the actual logged in user*/
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:/login";
        }

        //Get liked post from database
        Following following = followingService.findById(userId);

        User user = userService.findById(loggedinUser().getId());

        //Get all users' likes
        List<Following> userFollowing = user.getFollowings();

        //Check user already like the post
        for(Following following1: userFollowing){
            if(following1.getFollowing_user_id() == following.getFollowing_user_id()){

                //remove from PostLike table
                user.removeFollowing(following1);
               followingService.delete(following1);
                System.out.println("not following anymore");
                //return to page

                return "users/userProfile";
            }
        }

        Following postFollowing = new Following(user,following);
        user.addFollowing(postFollowing);
       followingService.save(postFollowing);
        System.out.println("followers added");

        model.addAttribute("user",loggedinUser());
        return "users/userProfile";
    }

    @PostMapping("/users/followers")
    public String followTestUser(@ModelAttribute Follower follower) {

        User userFollower = userDao.getReferenceById(follower.getFollower_user_id());

        /*Change this to the actual logged in user*/
        User user = userDao.getReferenceById(1L);

        List<Follower> followers = user.getFollowers();

        //Check user already followed the user
        for (Follower follower1 : followers) {
            if (follower1.getFollower_user_id() == userFollower.getId()) {

                //remove from PostLike table
                user.addFollower(follower1);
                userFollower.removeFollower(follower1);
                followerDao.delete(follower1);
                System.out.println("no more follwing " + follower1.getUser());
                //return to page
                return "users/followers";
            }
        }
        return "users/followers";
    }




    @GetMapping("/follow")
    public String testFollow(Model model) {
        model.addAttribute("following", new Following());
        return "/users/follow";
    }

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



    @GetMapping("/following")
    public String testFollowing(Model model) {
        return "users/following";
    }

    @PostMapping("/users/following")
    public String testFollowing(@ModelAttribute("following") Following following, @RequestParam("following_user_id") long following_user_id) {
        //Get UserId from logged-in user to create following
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Create new following
        following.setUser(user);
        following.setFollowing_user_id(following_user_id);
        //Save new following to database

        //        System.out.println(following.getFollowing_user_id());
        followingService.save(following);
        return "users/following";
    }








}
