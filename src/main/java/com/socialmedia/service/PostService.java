package com.socialmedia.service;

import com.socialmedia.dto.PostDto;
import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.utils.assemblers.PostModelAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.socialmedia.utils.assemblers.ConvertToDto.convertToDto;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostModelAssembler postModelAssembler;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository, PostModelAssembler postModelAssembler, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.postModelAssembler = postModelAssembler;
        this.likeRepository = likeRepository;
    }


    public ResponseEntity<PostDto> addPost(String content, User user) {
        Post post = new Post();
        post.setAuthor(user);
        post.setContent(content);
        postRepository.save(post);

        PostDto postDto = new PostDto();
        postDto.setAuthor(convertToDto(user));
        postDto.setLikesCount(0L);
        postDto.setCommentsCount(0L);
        postDto.setId(post.getId());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());
        postDto.setContent(content);
        postDto.setLikedByCurrentUser(false);

        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    public void likePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пост не найден"));
        if (!likeRepository.existsByPostAndUser(post, user)) {
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            likeRepository.save(like);
        }
    }

    public void unlikePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пост не найден"));
        Like like = likeRepository.findByPostAndUser(post, user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Лайк не найден"));
        likeRepository.delete(like);
    }

    public long countLikes(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public Page<PostDto> getPostsByUserId(Long userId, Pageable pageable) {
        Page<Object[]> results = postRepository.findAllByUserId(userId, pageable);

        List<PostDto> postDtos = results.stream().map(result -> {
            Post post = (Post) result[0];
            long commentCount = (long) result[1];
            long likeCount = (long) result[2];

            PostDto postDto = postModelAssembler.toModel(post);
            postDto.setCommentsCount(commentCount);
            postDto.setLikesCount(likeCount);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) auth.getPrincipal();
            boolean likedByUser = post.getLikes().stream()
                    .anyMatch(like -> like.getUser().getId().equals(currentUser.getId()));

            postDto.setLikedByCurrentUser(likedByUser);

            return postDto;
        }).toList();

        return new PageImpl<>(postDtos, pageable, results.getTotalElements());
    }
}