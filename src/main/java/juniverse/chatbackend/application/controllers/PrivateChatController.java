package juniverse.chatbackend.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import juniverse.chatbackend.application.dtos.ApiResponse;
import juniverse.chatbackend.application.dtos.private_chat.PrivateChatResponse;
import juniverse.chatbackend.application.dtos.private_message.MessageResponse;
import juniverse.chatbackend.domain.services.PrivateChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users/{userId}/chat-messages")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getUserChatMessages(@PathVariable Long userId) {
        try {
            // Retrieve chat messages for the user
            List<MessageResponse> messageResponses = privateChatService.getUserChatMessages(userId);


            boolean isGetUserChatMessagesFailed = messageResponses == null || messageResponses.isEmpty();

            // Build the API response
            ApiResponse<List<MessageResponse>> response = ApiResponse.<List<MessageResponse>>builder()
                    .success(!isGetUserChatMessagesFailed)
                    .message(isGetUserChatMessagesFailed ? "No message found for the user" : "Messages retrieved successfully")
                    .data(messageResponses)
                    .build();

            // Set the appropriate HTTP status
            HttpStatus status = isGetUserChatMessagesFailed ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return ResponseEntity.status(status).body(response);
        } catch (Exception e) {
            // Handle exceptions and return error response
            ApiResponse<List<MessageResponse>> response = ApiResponse.<List<MessageResponse>>builder()
                    .success(false)
                    .message("An error occurred: " + e.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
    }

    @Operation(
            summary = "Get all chats for a therapist",
            description = "Fetches all chats associated with the therapist by their ID which is id=2 because we have only one therapist."
    )
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/therapists/{therapistId}/chats")
    public ResponseEntity<ApiResponse<List<PrivateChatResponse>>> getTherapistChats(@PathVariable Long therapistId) {
        try {
            // Retrieve chats for the therapist
            List<PrivateChatResponse> chatResponses = privateChatService.getTherapistChats(therapistId);

            // Determine if no chats are found
            boolean isNoChatsFound = (chatResponses == null || chatResponses.isEmpty());

            // Build the API response
            ApiResponse<List<PrivateChatResponse>> response = ApiResponse.<List<PrivateChatResponse>>builder()
                    .success(!isNoChatsFound)
                    .message(isNoChatsFound ? "No chats found for the therapist" : "Chats retrieved successfully")
                    .data(chatResponses)
                    .build();

            // Set the appropriate HTTP status
            HttpStatus status = isNoChatsFound ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return ResponseEntity.status(status).body(response);
        } catch (Exception e) {
            // Handle exceptions and return error response
            ApiResponse<List<PrivateChatResponse>> response = ApiResponse.<List<PrivateChatResponse>>builder()
                    .success(false)
                    .message("An error occurred: " + e.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
    }

    @Operation(
            summary = "Get chat info by chat id"
    )
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("users/{userId}/chats/{chatId}/chat")
    public ResponseEntity<ApiResponse<PrivateChatResponse>> getPrivateChatById(@PathVariable Long chatId, @PathVariable Long userId) {
        try {
            //Retrieve chat by id
            PrivateChatResponse chatResponse = privateChatService.getPrivateChatById(chatId);

            // Determine if no chat is found
            boolean isChatNotFound = (chatResponse == null);

            //build api response
            ApiResponse<PrivateChatResponse> response = ApiResponse.<PrivateChatResponse>builder()
                    .success(!isChatNotFound)
                    .message(isChatNotFound ? "No chat is found for the given id" : "Chats retrieved successfully")
                    .data(chatResponse)
                    .build();

            // Set the appropriate HTTP status
            HttpStatus status = isChatNotFound ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return ResponseEntity.status(status).body(response);
        } catch (Exception e) {
            // Handle exceptions and return error response
            ApiResponse<PrivateChatResponse> response = ApiResponse.<PrivateChatResponse>builder()
                    .success(false)
                    .message("An error occurred: " + e.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
    }

    @Operation(
            summary = "update and mark a private chat received messages as READ when a user enters the chat"
    )
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("users/{userId}/chats/{chatId}/mark-read")
    public ResponseEntity<ApiResponse<Boolean>> markChatMessagesAsRead(@PathVariable Long userId,@PathVariable Long chatId) {
        try {
            //determine if message is updated as read
            Boolean isUpdated = privateChatService.markMessagesAsRead(userId, chatId);

            //build api response
            ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                    .success(isUpdated)
                    .message(!isUpdated ? "something went wrong" : "messages marked as read successfully")
                    .data(true)
                    .build();

            // Set the appropriate HTTP status
            HttpStatus status = !isUpdated ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return ResponseEntity.status(status).body(response);
        }catch (Exception e){
            // Handle exceptions and return error response
            ApiResponse<Boolean> response = ApiResponse.<Boolean>builder()
                    .success(false)
                    .message("An error occurred: " + e.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
    }

}
//
