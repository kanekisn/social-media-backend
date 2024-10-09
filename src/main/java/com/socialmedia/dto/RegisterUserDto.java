package com.socialmedia.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterUserDto {
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String description;

    private String city;

    private List<String> stack;
}
