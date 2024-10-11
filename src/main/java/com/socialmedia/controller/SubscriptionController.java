package com.socialmedia.controller;

import com.socialmedia.dto.UserDto;
import com.socialmedia.model.User;
import com.socialmedia.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{userId}/follow/{userToFollowId}")
    public ResponseEntity<String> followUser(@PathVariable Long userId, @PathVariable Long userToFollowId) {
        subscriptionService.followUser(userId, userToFollowId);
        return ResponseEntity.ok("Successfully followed the user");
    }

    @PostMapping("/{userId}/unfollow/{userToUnfollowId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long userId, @PathVariable Long userToUnfollowId) {
        subscriptionService.unfollowUser(userId, userToUnfollowId);
        return ResponseEntity.ok("Successfully unfollowed the user");
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<UserDto>> getFollowersById(@PathVariable Long userId) {
        Set<User> followers = subscriptionService.getFollowers(userId);
        return getSetResponseEntity(followers);
    }

    @GetMapping("/me/followers")
    public ResponseEntity<Set<UserDto>> getMyFollowers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Set<User> followers = subscriptionService.getFollowers(currentUser.getId());
        return getSetResponseEntity(followers);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<Set<UserDto>> getFollowing(@PathVariable Long userId) {
        Set<User> following = subscriptionService.getFollowing(userId);
        return getSetResponseEntity(following);
    }

    private ResponseEntity<Set<UserDto>> getSetResponseEntity(Set<User> following) {
        Set<UserDto> followingDTOS = following.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                    userDto.setCity(user.getCity());
                    userDto.setAvatarUrl(user.getAvatarUrl());
                    userDto.setStack(user.getStack());
                    return userDto;
                })
                .collect(Collectors.toSet());
        return ResponseEntity.ok(followingDTOS);
    }
}