package org.example.chatbackend.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.SendMessageRequest;
import org.example.chatbackend.application.dtos.MessageResponse;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.domain.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PostMapping("/send/{id}")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody SendMessageRequest request) {

        Message message = messageMapper.requestToModel(request);
        message.setChatId(0);
        return ResponseEntity.ok(messageMapper.modelToResponse(messageService.sendMessage(message)));
    }

    @GetMapping("/{id}")
    public List<MessageResponse> getAllMessages(@PathVariable Long id) {
        return messageMapper.modelListToResponseList(messageService.getAllMessages(id));
    }


}
