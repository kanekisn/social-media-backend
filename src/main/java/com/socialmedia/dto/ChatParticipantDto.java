package com.socialmedia.dto;

import com.socialmedia.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatParticipantDto {
    private Long id;
    private String username;
    private String avatarUrl;

    public static ChatParticipantDto fromUser(User user) {
        ChatParticipantDto dto = new ChatParticipantDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setAvatarUrl(user.getAvatarUrl());
        return dto;
    }
}