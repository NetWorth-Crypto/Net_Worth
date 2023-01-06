package com.example.networth.services;

import com.example.networth.models.Follower;
import com.example.networth.models.User;
import com.example.networth.repositories.FollowerRepository;

import java.util.List;

public class FollowerService {
    private final FollowerRepository followerDao;
    private final UserService userDao;

    public FollowerService(FollowerRepository followerDao, UserService userDao) {
        this.followerDao = followerDao;

        this.userDao = userDao;
    }


    public void removeFollower(long userId, long followerId){
        User user  = userDao.findById(userId);
        user.getFollowers().removeIf(x -> x.getId()==followerId);
        userDao.saveUser(user);
    }


    public List<Follower> getUserFollowers(User user){
        return user.getFollowers();
    }


    public Object findAll() {
        return followerDao.findAll();
    }

    public List<Follower> getUserNotFollowers(User user){
        return followerDao.getUserNotFollowers(user.getId());
    }

    public void save(Follower follower) {
        followerDao.save(follower);
    }

    public Follower findById(long id) {
        return followerDao.findById(id).get();
    }

    public void delete(Follower follower) {
        followerDao.delete(follower);
    }




}
