package com.socialmedia.controller;

import com.socialmedia.dto.CommentDto;
import com.socialmedia.model.Comment;
import com.socialmedia.model.User;
import com.socialmedia.service.CommentService;
import com.socialmedia.utils.assemblers.CommentModelAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentModelAssembler commentModelAssembler;

    public CommentController(CommentService commentService, CommentModelAssembler commentModelAssembler) {
        this.commentService = commentService;
        this.commentModelAssembler = commentModelAssembler;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PagedModel<CommentDto>> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentsPage = commentService.getCommentsByPostId(postId, pageable);

        List<CommentDto> commentDtos = commentsPage.getContent().stream()
                .map(commentModelAssembler::toModel)
                .toList();

        PagedModel<CommentDto> pagedModel = PagedModel.of(
                commentDtos,
                new PagedModel.PageMetadata(size, page, commentsPage.getTotalElements())
        );

        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/post/{postId}/add")
    public ResponseEntity<CommentDto> addComment(
            @PathVariable Long postId,
            @RequestBody Map<String, Object> payload,
            @AuthenticationPrincipal User currentUser) {
        return commentService.addComment((String) payload.get("content"), postId, currentUser);
    }
}