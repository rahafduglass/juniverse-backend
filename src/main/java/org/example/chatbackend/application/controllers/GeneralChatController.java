package org.example.chatbackend.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.SendMessageRequest;
import org.example.chatbackend.application.dtos.SendMessageResponse;
import org.example.chatbackend.domain.mappers.GeneralChatMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.domain.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/general")
public class GeneralChatController {

    private final MessageService messageService;
    private final GeneralChatMapper generalChatMapper;

    @PostMapping("/send")
    public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest request){
        Message message= generalChatMapper.requestToModel(request);
        return ResponseEntity.ok(generalChatMapper.modelToResponse(messageService.sendMessage(message)));
    }



}
