package com.socialmedia.service;

import com.socialmedia.model.User;
import com.socialmedia.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class SubscriptionService {
    private final UserRepository userRepository;

    public SubscriptionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void followUser(Long userId, Long userToFollowId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User userToFollow = userRepository.findById(userToFollowId)
                .orElseThrow(() -> new UsernameNotFoundException("User to follow not found"));

        if (user.getFollowing().add(userToFollow)) {
            userRepository.save(user);

            userToFollow.setSubscriptionAmount(
                    userToFollow.getSubscriptionAmount() + 1
            );
            userRepository.save(userToFollow);
        }
    }

    @Transactional
    public void unfollowUser(Long userId, Long userToUnfollowId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User userToUnfollow = userRepository.findById(userToUnfollowId)
                .orElseThrow(() -> new UsernameNotFoundException("User to unfollow not found"));

        if (user.getFollowing().remove(userToUnfollow)) {
            userRepository.save(user);

            userToUnfollow.setSubscriptionAmount(
                    userToUnfollow.getSubscriptionAmount() - 1
            );
            userRepository.save(userToUnfollow);
        }
    }

    public Set<User> getFollowers(Long userId) {
        User user = userRepository.findByIdWithFollowers(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getFollowers();
    }

    public Set<User> getFollowing(Long userId) {
        User user = userRepository.findByIdWithFollowing(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getFollowing();
    }
}