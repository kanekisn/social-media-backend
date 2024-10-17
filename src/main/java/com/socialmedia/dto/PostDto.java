package com.socialmedia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import java.util.Date;

@Getter
@Setter
public class PostDto extends RepresentationModel<PostDto> {
    private Long id;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private UserDto author;
    private Long likesCount;
    private boolean likedByCurrentUser;
    private Long commentsCount;
}