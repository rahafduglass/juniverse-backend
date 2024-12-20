package org.example.chatbackend.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.MessageResponse;
import org.example.chatbackend.application.dtos.SendMessageRequest;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.domain.services.MessageService;
import org.example.chatbackend.domain.services.PrivateChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/private")
public class PrivateChatController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final PrivateChatService privateChatService;

    @GetMapping("/{chatId}")
    public ResponseEntity<List<MessageResponse>> getAllMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageMapper.modelListToResponseList(messageService.getAllPrivateMessages(chatId)));
    }

    @PostMapping("/private/send")
    public ResponseEntity<MessageResponse> sendPrivateMessage(@RequestBody SendMessageRequest request, Long therapistId) {
        //check if chat exists

        Message message = messageMapper.requestToModel(request);
        return ResponseEntity.ok(messageMapper.modelToResponse(messageService.sendPrivateMessage(message,therapistId)));
    }

    @GetMapping("/private/{userId}")
    public ResponseEntity<Long> getPrivateChatId(@PathVariable Long userId) {
        return ResponseEntity.ok(privateChatService.getPrivateChatIdByUserId(userId));
    }



}
