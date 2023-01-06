package com.example.networth.repositories;

import com.example.networth.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowingRepository extends JpaRepository<Following, Long> {


//    @Query(
//            value = "SELECT * FROM following WHERE id NOT IN (SELECT following_id FROM user_following WHERE user_id = ?1)",
//            nativeQuery = true
//    )
//    List<Following> getUserNotFollowings(long user_Id);

    //Custom query
    //Query to search for other users by logged-in user.
    @Query(value = "SELECT user.first_name LIKE %:user%, user.last_name LIKE %:user%\n" +
            "FROM user\n" +
            "         JOIN following f on user.id = f.user_id;",nativeQuery = true)

    List<Following> findByUser(@Param("user") String username);
    User findByUsername(String username);


}
