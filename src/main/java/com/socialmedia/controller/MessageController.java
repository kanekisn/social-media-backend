package com.socialmedia.controller;

import com.socialmedia.dto.MessageDto;
import com.socialmedia.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    private final PagedResourcesAssembler<MessageDto> pagedResourcesAssembler;

    public MessageController(MessageService messageService, PagedResourcesAssembler<MessageDto> pagedResourcesAssembler) {
        this.messageService = messageService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<PagedModel<EntityModel<MessageDto>>> getMessages(
            @PathVariable Long chatId, Pageable pageable) {
        Page<MessageDto> messages = messageService.getChatMessages(chatId, pageable);
        PagedModel<EntityModel<MessageDto>> messageModels = pagedResourcesAssembler.toModel(messages);
        return ResponseEntity.ok(messageModels);
    }
}