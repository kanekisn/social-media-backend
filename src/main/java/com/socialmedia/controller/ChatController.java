package com.socialmedia.controller;

import com.socialmedia.dto.ChatDto;
import com.socialmedia.model.User;
import com.socialmedia.service.ChatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ChatService chatService;
    private final PagedResourcesAssembler<ChatDto> pagedResourcesAssembler;

    public ChatController(ChatService chatService, PagedResourcesAssembler<ChatDto> pagedResourcesAssembler) {
        this.chatService = chatService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/my-chats")
    public ResponseEntity<PagedModel<EntityModel<ChatDto>>> getUserChats(Pageable pageable, @AuthenticationPrincipal User currentUser) {
        Page<ChatDto> chats = chatService.getUserChats(currentUser, pageable);
        PagedModel<EntityModel<ChatDto>> chatModels = pagedResourcesAssembler.toModel(chats);
        return ResponseEntity.ok(chatModels);
    }
}