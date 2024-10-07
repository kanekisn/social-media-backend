package com.socialmedia.controller;

import com.socialmedia.response.UserResponse;
import com.socialmedia.service.UserService;
import org.springframework.http.ResponseEntity;
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
