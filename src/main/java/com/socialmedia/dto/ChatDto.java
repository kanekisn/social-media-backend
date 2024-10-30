package com.socialmedia.dto;

import com.socialmedia.model.Chat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ChatDto {
    private Long id;
    private Set<ChatParticipantDto> participants;
    private int messageCount;
    private String lastMessage;
    private LocalDateTime lastMessageTime;

    public static ChatDto fromEntity(Chat chat) {
        ChatDto dto = new ChatDto();
        dto.setId(chat.getId());
        dto.setMessageCount(chat.getMessages().size());
        dto.setLastMessage(chat.getLastMessage());
        dto.setLastMessageTime(chat.getLastMsgTime());
        dto.setParticipants(
                chat.getParticipants().stream()
                        .map(ChatParticipantDto::fromUser)
                        .collect(Collectors.toSet())
        );
        return dto;
    }
}