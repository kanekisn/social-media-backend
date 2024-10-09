package com.socialmedia.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String avatarUrl;

    private String city;

    private String description;

    private List<String> stack;
}