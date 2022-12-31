package com.example.networth.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserWithRoles extends User implements UserDetails {


    public UserWithRoles(User user) {
        super(user);  // Call the copy constructor defined in User
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
      List<GrantedAuthority>authorities = new ArrayList<>();
      for (Role role: roles){
          authorities.add(new SimpleGrantedAuthority(role.getType()));
      }
      return authorities;
    }




    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

public boolean hasRole(String roleName){return hasRole(roleName);}

}
