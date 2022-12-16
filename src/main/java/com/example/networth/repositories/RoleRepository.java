package com.example.networth.repositories;

import com.example.networth.models.Role;
import com.example.networth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

}
