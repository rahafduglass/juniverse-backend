package org.example.chatbackend.application.controllers;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.MessageResponse;
import org.example.chatbackend.application.dtos.SendMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/private")
public class PrivateChatController {


}

// api endpoints for chat/private/ =>
// 1. readMessage => mark message as read
// 2. getAllMessages => check first if chat exists connected to the requester id if requester isn't therapist? (check in model) id is userID otherwise its therapistID which depends on either chatID or receieverID (frontend state)
// 3. sendMessage => check first if chat exists , create new one, or send to existing chat.
// 4. getTherapistChats => by using his id (the only user that's connected to many private chats) I return all chats connected to therapist type
// 5. getChatUnreadMessagesNumber => how many isRead = false in a private chat, depends on getAllMessages.