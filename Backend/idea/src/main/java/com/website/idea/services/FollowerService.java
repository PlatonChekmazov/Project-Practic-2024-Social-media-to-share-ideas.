package com.website.idea.services;

import com.website.idea.models.Followers.Follower;
import com.website.idea.repositories.FollowerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    @Autowired
    private FollowerRepository followerRepository;

    public List<Follower> getAllFollowers() {
        return followerRepository.findAll();
    }

    public Optional<Follower> getFollowerById(Long id) {
        return followerRepository.findById(id);
    }

    public Follower createFollower(Follower follower) {
        return followerRepository.save(follower);
    }

    public void deleteFollower(Long id) {
        Follower follower = followerRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Подписчик не найден с id: " + id));
        followerRepository.delete(follower);
    }
}
