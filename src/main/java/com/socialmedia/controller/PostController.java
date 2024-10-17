package com.socialmedia.controller;

import com.socialmedia.dto.PostDto;
import com.socialmedia.model.User;
import com.socialmedia.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
        postService.likePost(postId, currentUser);
        return ResponseEntity.ok().body(Map.of("message", "Post liked successfully"));
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
        postService.unlikePost(postId, currentUser);
        return ResponseEntity.ok().body(Map.of("message", "Post unliked successfully"));
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<Long> getPostLikes(@PathVariable Long postId) {
        long likesCount = postService.countLikes(postId);
        return ResponseEntity.ok(likesCount);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody Map<String, Object> payload, @AuthenticationPrincipal User currentUser) {
        return postService.addPost((String) payload.get("content"), currentUser);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedModel<PostDto>> getPostsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostDto> postsPage = postService.getPostsByUserId(userId, pageable);

        PagedModel<PostDto> pagedModel = PagedModel.of(
                postsPage.getContent(),
                new PagedModel.PageMetadata(
                        postsPage.getSize(),
                        postsPage.getNumber(),
                        postsPage.getTotalElements()
                )
        );

        return ResponseEntity.ok(pagedModel);
    }
}