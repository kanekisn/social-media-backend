package com.socialmedia.controller;

import com.socialmedia.dto.MessageDto;
import com.socialmedia.model.Message;
import com.socialmedia.service.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    private final MessageService messageService;

    public ChatWebSocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/messages/{chatId}")
    public MessageDto sendMessage(@DestinationVariable Long chatId, @Payload MessageDto messageDto) {
        Message savedMessage = messageService.sendMessage(chatId, messageDto.getSenderUsername(), messageDto.getContent());
        return MessageDto.fromEntity(savedMessage);
    }
}