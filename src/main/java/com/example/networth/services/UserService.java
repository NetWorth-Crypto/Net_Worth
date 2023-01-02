package com.example.networth.services;



import com.example.networth.models.Role;
import com.example.networth.models.User;
import com.example.networth.repositories.RoleRepository;
import com.example.networth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {


    private final RoleRepository roleDao;
    private final UserRepository userDao;

    public UserService(RoleRepository roleDao, UserRepository userDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;

    }

    public User findById(long id){
        Optional<User> user = userDao.findById(id);
        return user.orElse(null);
    }

    public void saveUser(User user){
        userDao.save(user);
    }


    public List<Role> getUserNotRoles(User user){
        return roleDao.getUserNotRoles(user.getId());
    }

    public List<User> findAll() {
        return  userDao.findAll();
    }

    public void delete(User user) {
        userDao.delete(user);
    }
}
