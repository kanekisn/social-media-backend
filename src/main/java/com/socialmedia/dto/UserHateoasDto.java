package com.socialmedia.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class UserHateoasDto extends RepresentationModel<UserHateoasDto> {
    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String avatarUrl;
}