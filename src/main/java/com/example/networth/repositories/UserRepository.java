package com.example.networth.repositories;

import com.example.networth.models.Post;
import com.example.networth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    //Custom query
    //Query to search for other users by logged-in user.
    @Query(value = "SELECT * FROM user u WHERE u.first_name LIKE %:user% OR u.last_name LIKE %:user% OR u.user_name LIKE %:user% OR u.email LIKE %:user% OR u.role_id LIKE %:user% OR u.user_title LIKE %:user%",nativeQuery = true)
    List<User> findByUser(@Param("user") String username);
    User findByUsername(String username);
}
