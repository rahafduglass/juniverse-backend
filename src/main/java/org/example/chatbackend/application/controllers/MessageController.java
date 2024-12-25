package org.example.chatbackend.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.private_message.MessageResponse;
import org.example.chatbackend.application.dtos.private_message.MessageRequest;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.MessageModel;
import org.example.chatbackend.domain.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendPirvateMessage(@RequestBody MessageRequest messageRequest) {
        MessageModel messageModel= messageMapper.requestToModel(messageRequest);
        return  ResponseEntity.ok(messageMapper.modelToResponse(messageService.sendPrivateMessage(messageModel)));
    }


}
// 1. readMessage => mark message as read (put)
// 2. getAllMessages => check first if chat exists connected to the requester id if requester isn't therapist? (check in model) id is userID otherwise its therapistID which depends on either chatID or receieverID (frontend state)
// 3. sendMessage => check first if chat exists , create new one, or send to existing chat.
