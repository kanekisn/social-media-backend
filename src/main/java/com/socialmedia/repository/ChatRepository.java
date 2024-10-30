package com.socialmedia.repository;

import com.socialmedia.model.Chat;
import com.socialmedia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE :user MEMBER OF c.participants")
    Page<Chat> findAllByParticipantsContaining(User user, Pageable pageable);
}