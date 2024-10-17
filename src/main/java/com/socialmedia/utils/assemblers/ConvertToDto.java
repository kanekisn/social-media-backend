package com.socialmedia.utils.assemblers;

import com.socialmedia.dto.UserDto;
import com.socialmedia.model.User;

public class ConvertToDto {
    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAvatarUrl(user.getAvatarUrl());

        return userDto;
    }
}