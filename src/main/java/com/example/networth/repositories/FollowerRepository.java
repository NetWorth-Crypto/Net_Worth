package com.example.networth.repositories;

import com.example.networth.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {

    @Query(value = "SELECT user.first_name LIKE %:user%, user.last_name LIKE %:user%\n" +
            "FROM user\n" +
            "         JOIN follower f on user.id = f.user_id;",nativeQuery = true)

    List<Follower> findFollowerBy(@RequestParam("user") String username);
    User findByFollower(String follower);


//    @Query(
//            value = "SELECT * FROM follower WHERE id NOT IN (SELECT follower_id FROM user_follower WHERE user_id = ?1)",
//            nativeQuery = true
//    )
//    List<Follower> getUserNotFollowers(long user_Id);
//


}
