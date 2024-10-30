package com.socialmedia.service;

import com.socialmedia.dto.MessageDto;
import com.socialmedia.model.Chat;
import com.socialmedia.model.Message;
import com.socialmedia.model.User;
import com.socialmedia.repository.ChatRepository;
import com.socialmedia.repository.MessageRepository;
import com.socialmedia.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Message sendMessage(Long chatId, String senderUsername, String content) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        chat.setLastMessage(senderUsername + ": " + content);
        chat.setLastMsgTime(LocalDateTime.now());

        chatRepository.save(chat);

        return messageRepository.save(message);
    }

    public Page<MessageDto> getChatMessages(Long chatId, Pageable pageable) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        Page<Message> messages = messageRepository.findByChatOrderByTimestampDesc(chat, pageable);
        return messages.map(MessageDto::fromEntity);
    }
}