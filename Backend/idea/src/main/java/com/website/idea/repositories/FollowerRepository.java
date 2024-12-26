package com.website.idea.repositories;

import com.website.idea.models.Followers.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findByUserId(Long userId);
    List<Follower> findByFollowerId(Long followerId);
}