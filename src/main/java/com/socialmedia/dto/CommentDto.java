package com.socialmedia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
public class CommentDto extends RepresentationModel<CommentDto> {
    private Long id;
    private String content;
    private Date createdAt;
    private UserDto author;
}