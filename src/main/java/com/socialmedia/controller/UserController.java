package com.socialmedia.controller;

import com.socialmedia.model.User;
import com.socialmedia.response.UserResponse;
import com.socialmedia.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("me/")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        UserResponse userResponse = new UserResponse();

        userResponse.setUsername(currentUser.getUsername());
        userResponse.setAvatarUrl(currentUser.getAvatarUrl());
        userResponse.setId(currentUser.getId());
        userResponse.setCity(currentUser.getCity());
        userResponse.setSubscriptionAmount(currentUser.getSubscriptionAmount());
        userResponse.setStack(currentUser.getStack());
        userResponse.setLastName(currentUser.getLastName());
        userResponse.setFirstName(currentUser.getFirstName());

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("users/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> result = userService.getAllUsers().stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setCity(user.getCity());
            userResponse.setStack(user.getStack());
            userResponse.setSubscriptionAmount(user.getSubscriptionAmount());
            userResponse.setAvatarUrl(user.getAvatarUrl());
            return userResponse;
        }).toList();
        return ResponseEntity.ok(result);
    }
}