package juniverse.chatbackend.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.application.dtos.private_message.MessageRequest;
import juniverse.chatbackend.application.dtos.private_message.MessageResponse;
import juniverse.chatbackend.application.dtos.ApiResponse;

import juniverse.chatbackend.domain.mappers.MessageMapper;
import juniverse.chatbackend.domain.models.MessageModel;
import juniverse.chatbackend.domain.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/message")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;


    @Operation(
            summary = "if a user wants to initialize a chat or resume an already initialized chat they send their ID ",
            description = "1. if the sender is a regular user the receiver id=2 because we have one therapist, " +
                    "2. if the sender is a therapist, the receiver id depends on the userId that was already fetched in therapist chats."
    )
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/private/send")
    public ResponseEntity<ApiResponse<MessageResponse>> sendPrivateMessage(@RequestBody MessageRequest messageRequest) {
        try {
            // Convert the request to a model
            MessageModel messageModel = messageMapper.requestToModel(messageRequest);

            // Send the private message and map the result to the response
            MessageResponse messageResponse = messageMapper.modelToResponse(messageService.sendPrivateMessage(messageModel));

            // Build the API response
            ApiResponse<MessageResponse> response = ApiResponse.<MessageResponse>builder()
                    .success(true)
                    .message("Message sent successfully")
                    .data(messageResponse)
                    .build();

            return ResponseEntity.ok(response); // HTTP 200: OK
        } catch (Exception e) {
            // Handle exceptions and return an error response
            ApiResponse<MessageResponse> response = ApiResponse.<MessageResponse>builder()
                    .success(false)
                    .message("Failed to send message: " + e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response); // HTTP 417: Expectation Failed
        }
    }

    @Operation(
            summary = "when the receiver enters the private chat all the sent messages in that chat are marked read",
            description = "1. when the therapist enters a private chat the messages sent from the user will be marked as read" +
                    "2. when the user enters a private chat the messages sent from the therapist will be marked as read."
    )
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/private/read/{messageId}")
    public ResponseEntity<ApiResponse<Long>> readMessage(@PathVariable Long messageId) {
        try {
            // Mark the message as read and retrieve the ID of the read message
            Long readMessageId = messageService.readMessage(messageId);

            // Build the API response
            ApiResponse<Long> response = ApiResponse.<Long>builder()
                    .success(true)
                    .message("Message marked as read successfully")
                    .data(readMessageId)
                    .build();

            return ResponseEntity.ok(response); // HTTP 200: OK
        } catch (Exception e) {
            // Handle case where the message ID does not exist
            ApiResponse<Long> response = ApiResponse.<Long>builder()
                    .success(false)
                    .message("Message not found: " + e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // HTTP 404: Not Found
        }
    }

}
