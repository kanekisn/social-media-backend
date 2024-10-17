package com.socialmedia.service;

import com.socialmedia.dto.CommentDto;
import com.socialmedia.model.Comment;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.socialmedia.utils.assemblers.ConvertToDto.convertToDto;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Page<Comment> getCommentsByPostId(Long postId, Pageable pageable) {
        return commentRepository.findAllByPostId(postId, pageable);
    }

    public ResponseEntity<CommentDto> addComment(String content, Long postId, User user) {
        Comment comment = new Comment();
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пост не найден"));
        comment.setPost(post);
        comment.setAuthor(user);
        comment.setContent(content);
        commentRepository.save(comment);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(content);
        commentDto.setAuthor(convertToDto(user));
        commentDto.setCreatedAt(comment.getCreatedAt());

        return ResponseEntity.ok(commentDto);
    }
}