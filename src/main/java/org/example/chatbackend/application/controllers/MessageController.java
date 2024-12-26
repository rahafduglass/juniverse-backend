package org.example.chatbackend.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.private_message.MessageResponse;
import org.example.chatbackend.application.dtos.private_message.MessageRequest;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.MessageModel;
import org.example.chatbackend.domain.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/message")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PostMapping("/private/send")
    public ResponseEntity<MessageResponse> sendPirvateMessage(@RequestBody MessageRequest messageRequest) {
        MessageModel messageModel= messageMapper.requestToModel(messageRequest);
        return  ResponseEntity.ok(messageMapper.modelToResponse(messageService.sendPrivateMessage(messageModel)));
    }

    @PutMapping("/private/read/{messageId}")
    public ResponseEntity<Long> readMessage(@PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.readMessage(messageId));
    }

}
