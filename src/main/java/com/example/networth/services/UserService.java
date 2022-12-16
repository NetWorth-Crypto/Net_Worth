package com.example.networth.services;



import com.example.networth.models.User;
import com.example.networth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public class UserService {



    private final UserRepository userDao;

    public UserService( UserRepository userDao) {
        this.userDao = userDao;

    }

    public User findById(long id){
        Optional<User> user = userDao.findById(id);
        return user.orElse(null);
    }

    public void saveUser(User user){
        userDao.save(user);
    }



}
