package com.example.networth.services;

import com.example.networth.models.Role;
import com.example.networth.models.User;
import com.example.networth.repositories.RoleRepository;
import com.example.networth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
   private final RoleRepository roleDao;
    private final UserRepository userDao;

    public RoleService(RoleRepository roleDao, UserRepository userDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
    }


    //Assign Role to User
    public void assignUserRole(long userId, long roleId){
        User user  = userDao.findById(userId).orElse(null);
        Role role = roleDao.findById(roleId).orElse(null);
        List<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userDao.save(user);
    }


    //Unassign Role to User
    public void unassignUserRole(long userId, long roleId){
        User user  = userDao.findById(userId).orElse(null);
        user.getRoles().removeIf(x -> x.getId()==roleId);
        userDao.save(user);
    }

    public List<Role> getUserRoles(User user){
        return user.getRoles();
    }


    public Object findAll() {
        return roleDao.findAll();
    }

    public List<Role> getUserNotRoles(User user){
        return roleDao.getUserNotRoles(user.getId());
    }

    public void save(Role role) {
        roleDao.save(role);
    }

    public Role findById(long id) {
        return roleDao.findById(id).get();
    }

    public void delete(Role role) {
        roleDao.delete(role);
    }
}
