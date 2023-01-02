package com.example.networth.repositories;

import com.example.networth.models.Follower;
import com.example.networth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    @Query(value = "select first_name,last_name,profile_picture,user_title from user\n" +
            "where user.id IN  (\n" +
            "    select follower_user_id\n" +
            "    from follower\n" +
            ")",nativeQuery = true)
    List<Follower> findByfollower(@Param("follower") String follower);
    Follower findByFollower(String follower);

}
