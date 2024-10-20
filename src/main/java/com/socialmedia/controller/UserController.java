package com.socialmedia.controller;

import com.socialmedia.dto.UserDto;
import com.socialmedia.model.User;
import com.socialmedia.response.UserResponse;
import com.socialmedia.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

        return ResponseEntity.ok(userResponseFactory(user));
    }

    @PatchMapping("me")
    public ResponseEntity<?> patchUser(@RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        userService.patchUser(userDto, user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> result = userService.getAllUsers().stream().map(this::userResponseFactory).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("get-user/{id}")
    public ResponseEntity<UserResponse> getAllUsers(@PathVariable Long id) {
        User user = userService.getUserById(id);

        return ResponseEntity.ok(userResponseFactory(user));
    }

    @PostMapping("upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User currentUser) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String filename = file.getOriginalFilename();

        if (filename == null || (!filename.endsWith(".png") && !filename.endsWith(".jpg") && !filename.endsWith(".jpeg"))) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String UPLOAD_DIR = "src/main/resources/static/uploads/";

            String extension = filename.substring(filename.lastIndexOf("."));
            String newFileName = UUID.randomUUID() + extension;
            Path path = Paths.get(UPLOAD_DIR + newFileName);

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            System.out.println("Файл сохранён по пути: " + path.toAbsolutePath());

            Files.write(path, file.getBytes());

            String fileUrl = "http://localhost:8080/uploads/" + newFileName;

            userService.updateAvatar(currentUser, fileUrl);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private UserResponse userResponseFactory(User user) {
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
    }
}