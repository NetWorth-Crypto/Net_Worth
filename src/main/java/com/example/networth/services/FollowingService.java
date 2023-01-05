package com.example.networth.services;


import com.example.networth.models.Following;
import com.example.networth.models.User;
import com.example.networth.repositories.FollowingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingService {

    private final FollowingRepository followingDao;

    private final UserService userDao;

    public FollowingService(FollowingRepository followingDao, UserService userDao) {
        this.followingDao = followingDao;
        this.userDao = userDao;

    }




    public void removeFollowing(long userId, long followingId){
        User user  = userDao.findById(userId);
        user.getFollowings().removeIf(x -> x.getId()==followingId);
        userDao.saveUser(user);
    }


    public List<Following> getUserFollowings(User user){
        return user.getFollowings();
    }



    public Object findAll() {
        return followingDao.findAll();
    }

    public List<Following> getUserNotFollowings(User user){
        return followingDao.getUserNotFollowings(user.getId());
    }

    public void save(Following following) {
        followingDao.save(following);
    }

    public Following findById(long id) {
        return followingDao.findById(id).get();
    }

    public void delete(Following following) {
        followingDao.delete(following);
    }







}
