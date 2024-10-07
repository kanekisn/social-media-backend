package com.socialmedia.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String city;
    private List<String> stack;
    private Long subscriptionAmount;
    private String avatarUrl;
}
