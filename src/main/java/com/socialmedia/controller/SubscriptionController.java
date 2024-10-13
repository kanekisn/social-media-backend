package com.socialmedia.controller;

import com.socialmedia.dto.UserHateoasDto;
import com.socialmedia.model.User;
import com.socialmedia.service.SubscriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    public SubscriptionController(SubscriptionService subscriptionService,
                                  PagedResourcesAssembler<User> pagedResourcesAssembler) {
        this.subscriptionService = subscriptionService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
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
    public ResponseEntity<PagedModel<UserHateoasDto>> getFollowersById(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> followersPage = subscriptionService.getFollowers(userId, pageable);
        PagedModel<UserHateoasDto> followersDtoPage = pagedResourcesAssembler.toModel(followersPage, this::convertToDto);

        return ResponseEntity.ok(followersDtoPage);
    }

    @GetMapping("/me/followers")
    public ResponseEntity<PagedModel<UserHateoasDto>> getMyFollowers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size);
        Page<User> followersPage = subscriptionService.getFollowers(currentUser.getId(), pageable);
        PagedModel<UserHateoasDto> followersDtoPage = pagedResourcesAssembler.toModel(followersPage, this::convertToDto);

        return ResponseEntity.ok(followersDtoPage);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<PagedModel<UserHateoasDto>> getFollowing(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> followingPage = subscriptionService.getFollowing(userId, pageable);
        PagedModel<UserHateoasDto> followingDtoPage = pagedResourcesAssembler.toModel(followingPage, this::convertToDto);

        return ResponseEntity.ok(followingDtoPage);
    }

    private UserHateoasDto convertToDto(User user) {
        UserHateoasDto userDto = new UserHateoasDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setCity(user.getCity());
        userDto.setAvatarUrl(user.getAvatarUrl());

        userDto.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(SubscriptionController.class).getFollowersById(user.getId(), 0, 10))
                .withRel("followers"));
        userDto.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(SubscriptionController.class).getFollowing(user.getId(), 0, 10))
                .withRel("following"));

        return userDto;
    }
}