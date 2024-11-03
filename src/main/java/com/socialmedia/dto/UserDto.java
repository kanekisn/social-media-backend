package com.socialmedia.dto;

import com.socialmedia.model.User;
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

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setCity(user.getCity());
        dto.setDescription(user.getDescription());
        dto.setStack(user.getStack());
        return dto;
    }
}