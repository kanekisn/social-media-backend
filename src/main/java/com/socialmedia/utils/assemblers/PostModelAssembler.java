package com.socialmedia.utils.assemblers;

import com.socialmedia.controller.CommentController;
import com.socialmedia.controller.PostController;
import com.socialmedia.dto.PostDto;
import com.socialmedia.model.Post;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static com.socialmedia.utils.assemblers.ConvertToDto.convertToDto;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler extends RepresentationModelAssemblerSupport<Post, PostDto> {

    public PostModelAssembler() {
        super(PostController.class, PostDto.class);
    }

    @NonNull
    @Override
    public PostDto toModel(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setAuthor(convertToDto(post.getAuthor()));
        dto.add(linkTo(methodOn(PostController.class).getPostsByUserId(post.getId(), 0, 10)).withSelfRel());
        dto.add(linkTo(methodOn(CommentController.class).getCommentsByPostId(post.getId(), 0, 5)).withRel("comments"));

        return dto;
    }
}