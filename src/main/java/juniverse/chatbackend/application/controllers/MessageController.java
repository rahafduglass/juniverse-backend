package juniverse.chatbackend.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import juniverse.chatbackend.application.helpers.ApiResponseHelper;
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
    private final ApiResponseHelper apiResponseHelper;

    @Operation(
            summary = "when the receiver enters the private chat all the sent messages in that chat are marked read",
            description = "1. when the therapist enters a private chat the messages sent from the user will be marked as read" +
                    "2. when the user enters a private chat the messages sent from the therapist will be marked as read."
    )
    public ResponseEntity<ApiResponse<Long>> readMessage(@PathVariable Long messageId) {
        try {
            // Mark the message as read and retrieve the ID of the read message
            Long readMessageId = messageService.readMessage(messageId);

            return apiResponseHelper.buildApiResponse(readMessageId,true, "Message marked as read successfully" , HttpStatus.OK);
        } catch (Exception e) {
            // Handle case where the message ID does not exist
            return apiResponseHelper.buildApiResponse(null, false, "an error occurred " + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }



}
