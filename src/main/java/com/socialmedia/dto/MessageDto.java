package com.socialmedia.dto;

import com.socialmedia.model.Message;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDto {
    private Long id;
    private String content;
    private String senderUsername;
    private Long senderId;
    private LocalDateTime timestamp;
    private Long chatId;

    public static MessageDto fromEntity(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setSenderUsername(message.getSender().getUsername());
        dto.setSenderId(message.getSender().getId());
        dto.setTimestamp(message.getTimestamp());
        dto.setChatId(message.getChat().getId());
        return dto;
    }
}