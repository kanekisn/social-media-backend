package com.socialmedia.controller;

import com.socialmedia.dto.UserDto;
import com.socialmedia.model.User;
import com.socialmedia.response.UserResponse;
import com.socialmedia.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/users/")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("me")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCity(user.getCity());
        userResponse.setStack(userService.getUserStack(user));
        userResponse.setSubscriptionAmount(user.getSubscriptionAmount());
        userResponse.setDescription(user.getDescription());
        userResponse.setAvatarUrl(user.getAvatarUrl());

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> result = userService.getAllUsers().stream().map(user -> {
            Hibernate.initialize(user.getStack());

            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setCity(user.getCity());
            userResponse.setStack(user.getStack());
            userResponse.setSubscriptionAmount(user.getSubscriptionAmount());
            userResponse.setDescription(user.getDescription());
            userResponse.setAvatarUrl(user.getAvatarUrl());
            return userResponse;
        }).toList();
        return ResponseEntity.ok(result);
    }

    @PatchMapping("update")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        userService.updateUser(userDto, currentUser);

        return ResponseEntity.ok("User updated successfully");
    }
}