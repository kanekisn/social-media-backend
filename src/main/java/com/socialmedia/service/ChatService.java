package com.socialmedia.service;

import com.socialmedia.dto.ChatDto;
import com.socialmedia.model.Chat;
import com.socialmedia.model.User;
import com.socialmedia.repository.ChatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Page<ChatDto> getUserChats(User user, Pageable pageable) {
        Page<Chat> chats = chatRepository.findAllByParticipantsContaining(user, pageable);
        return chats.map(ChatDto::fromEntity);
    }
}