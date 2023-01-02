package com.example.networth.repositories;

import com.example.networth.models.Follower;
import com.example.networth.models.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowingRepository extends JpaRepository<Following,Long> {

    @Query(value = "SELECT first_name LIKE %:followingUser%,last_name LIKE %:followingUser%,profile_picture,user_title from user\n" +
            "where user.id IN  (\n" +
            "    select following_user_id\n" +
            "    from following\n" +
            ")",nativeQuery = true)
    List<Following> findFollowingBy(@Param("followingUser") String follower);
    Following findFollowingBy(String following);

}
