package com.socialmedia.repository;

import com.socialmedia.model.Chat;
import com.socialmedia.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Page<Message> findByChatOrderByTimestampDesc(Chat chat, Pageable pageable);
}