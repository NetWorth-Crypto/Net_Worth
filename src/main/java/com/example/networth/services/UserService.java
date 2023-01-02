package com.example.networth.services;



import com.example.networth.models.Post;
import com.example.networth.models.User;
import com.example.networth.repositories.SearchPostRepository;
import com.example.networth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {



    private final UserRepository userDao;

    @Autowired
    private UserRepository userRepository;
    //creating a list of a user using users detail.
    public List<User> getByUser(String user) throws UsernameNotFoundException {
        List<User> lists = (List<User>) userRepository.findByUser(user);
        if (lists == null) {
            throw new UsernameNotFoundException("No user found for " + user);
        }
        return lists;
    }

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




    public List<User> findAll() {
        return  userDao.findAll();
    }

    public void delete(User user) {
        userDao.delete(user);
    }

}
