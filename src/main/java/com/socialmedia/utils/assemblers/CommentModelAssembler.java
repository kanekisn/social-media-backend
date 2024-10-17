package com.socialmedia.utils.assemblers;

import com.socialmedia.controller.CommentController;
import com.socialmedia.dto.CommentDto;
import com.socialmedia.model.Comment;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static com.socialmedia.utils.assemblers.ConvertToDto.convertToDto;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentModelAssembler extends RepresentationModelAssemblerSupport<Comment, CommentDto> {

    public CommentModelAssembler() {
        super(CommentController.class, CommentDto.class);
    }

    @NonNull
    @Override
    public CommentDto toModel(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setAuthor(convertToDto(comment.getAuthor()));

        dto.add(linkTo(methodOn(CommentController.class).getCommentsByPostId(comment.getPost().getId(), 0, 5)).withRel("post"));

        return dto;
    }
}