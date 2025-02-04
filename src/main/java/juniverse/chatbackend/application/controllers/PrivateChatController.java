package juniverse.chatbackend.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import juniverse.chatbackend.application.dtos.private_message.MessageRequest;
import juniverse.chatbackend.application.helpers.ApiResponseHelper;
import juniverse.chatbackend.domain.mappers.MessageMapper;
import juniverse.chatbackend.domain.services.MessageService;
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
@RequestMapping("/api/v1/private-chat")
public class PrivateChatController {

    private final PrivateChatService privateChatService;
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ApiResponseHelper apiResponseHelper;


    @GetMapping("/{userId}/messages")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getUserChatMessages(@PathVariable Long userId) {
        try {
            // Retrieve chat messages for the user
            List<MessageResponse> messageListResponse = privateChatService.getUserChatMessages(userId);
            //check if retrieval succeeded
            boolean isGetUserChatMessagesFailed = messageListResponse == null || messageListResponse.isEmpty();
            //build response
            return apiResponseHelper.buildApiResponse(messageListResponse, !isGetUserChatMessagesFailed
                    , (isGetUserChatMessagesFailed ? "No message found for the user" : "Messages retrieved successfully")
                    , (isGetUserChatMessagesFailed ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            // Handle exceptions and return error response
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }


    @GetMapping("/therapists/{therapistId}/chats")
    public ResponseEntity<ApiResponse<List<PrivateChatResponse>>> getTherapistChats(@PathVariable Long therapistId) {
        try {
            // Retrieve chats for the therapist
            List<PrivateChatResponse> chatResponses = privateChatService.getTherapistChats(therapistId);

            // Determine if no chats are found
            boolean isNoChatsFound = (chatResponses == null || chatResponses.isEmpty());

            //build response
            return apiResponseHelper.buildApiResponse(chatResponses, !isNoChatsFound
                    , (isNoChatsFound ? "No chats found for the therapist" : "Chats retrieved successfully")
                    , (isNoChatsFound ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }


    @GetMapping("/{chatId}")
    public ResponseEntity<ApiResponse<PrivateChatResponse>> getPrivateChatById(@PathVariable Long chatId) {
        try {
            //Retrieve chat by id
            PrivateChatResponse chatResponse = privateChatService.getPrivateChatById(chatId);

            // Determine if no chat is found
            boolean isChatNotFound = (chatResponse == null);
            //build response
            return apiResponseHelper.buildApiResponse(chatResponse, !isChatNotFound
                    , (isChatNotFound ? "No chat is found" : "Chat retrieved successfully")
                    , isChatNotFound ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }


    @PutMapping("/{chatId}/{userId}/mark-as-read")
    public ResponseEntity<ApiResponse<Boolean>> markChatMessagesAsRead(@PathVariable Long userId, @PathVariable Long chatId) {
        try {
            Boolean isUpdated = privateChatService.markMessagesAsRead(userId, chatId);
            return apiResponseHelper.buildApiResponse(isUpdated, true, (!isUpdated ? "something went wrong" : "messages marked as read successfully"), (!isUpdated ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }


    @PostMapping("/message")
    public ResponseEntity<ApiResponse<MessageResponse>> sendPrivateMessage(@RequestBody MessageRequest messageRequest) {
        try {
            if (messageRequest.getContent().isEmpty()) throw new Exception("message content is empty");
            MessageResponse messageResponse = messageMapper.modelToResponse(messageService.sendPrivateMessage(messageMapper.requestToModel(messageRequest)));
            return apiResponseHelper.buildApiResponse(messageResponse, true, "Message sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "Failed to send message: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
//
