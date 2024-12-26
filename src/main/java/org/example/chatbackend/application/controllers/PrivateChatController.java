package org.example.chatbackend.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.chatbackend.application.dtos.private_chat.PrivateChatResponse;
import org.example.chatbackend.application.dtos.private_message.MessageResponse;
import org.example.chatbackend.domain.services.PrivateChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/chat/private")
public class PrivateChatController {

    private final PrivateChatService privateChatService;



    @Operation(
            summary = "Get all messages for a private chat(therapist-user)",
            description = "fetches all messages associated with the userId only not therapistId."
    )
    @GetMapping("/users/{userId}/chat-messages")
    public ResponseEntity<List<MessageResponse>> getUserChatMessages(@PathVariable Long userId){
        return ResponseEntity.ok(privateChatService.getUserChatMessages(userId));
    }



    @Operation(
            summary = "Get all chats for a therapist",
            description = "Fetches all chat sessions associated with a specific therapist by their ID which is for now only 2."
    )
    @GetMapping("/therapists/{therapistId}/chats")
    public ResponseEntity<List<PrivateChatResponse>> getTherapistChats(@PathVariable Long therapistId){
        return ResponseEntity.ok(privateChatService.getTherapistChats(therapistId));
    }

}

