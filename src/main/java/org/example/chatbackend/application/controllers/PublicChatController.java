package org.example.chatbackend.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.MessageResponse;
import org.example.chatbackend.application.dtos.SendMessageRequest;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.domain.services.MessageService;
import org.example.chatbackend.domain.services.PublicChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/public")
public class PublicChatController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final PublicChatService publicChatService;

    @GetMapping("/{chatId}")
    public ResponseEntity<List<MessageResponse>> getAllPublicMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageMapper.modelListToResponseList(messageService.getAllPublicMessages(chatId)));
    }

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendPublicMessage(@RequestBody SendMessageRequest request) {
        Message message = messageMapper.requestToModel(request);
        return ResponseEntity.ok(messageMapper.modelToResponse(messageService.sendPublicMessage(message)));
    }

    @GetMapping("/public")
    public ResponseEntity<Long> getPublicChatId(){
        return ResponseEntity.ok(publicChatService.getPublicChatId());
    }

}
