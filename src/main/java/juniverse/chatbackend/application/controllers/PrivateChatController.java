package juniverse.chatbackend.application.controllers;

import juniverse.chatbackend.application.dtos.ApiResponse;
import juniverse.chatbackend.application.dtos.private_chat.TherapistChatResponse;
import juniverse.chatbackend.application.dtos.private_chat.messages.MessageRequest;
import juniverse.chatbackend.application.dtos.private_chat.messages.MessageResponse;
import juniverse.chatbackend.application.dtos.private_chat.messages.MessageResponseNew;
import juniverse.chatbackend.application.helpers.ApiResponseHelper;
import juniverse.chatbackend.domain.mappers.MessageMapper;
import juniverse.chatbackend.domain.services.MessageService;
import juniverse.chatbackend.domain.services.PrivateChatService;
import lombok.AllArgsConstructor;
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



    @GetMapping("/{chatId}")
    public ResponseEntity<ApiResponse<TherapistChatResponse>> getPrivateChatById(@PathVariable Long chatId) {
        try {
            //Retrieve chat by id
            TherapistChatResponse therapistChatResponse = privateChatService.getPrivateChatById(chatId);

            // Determine if no chat is found
            boolean isChatNotFound = (therapistChatResponse == null);
            //build response
            return apiResponseHelper.buildApiResponse(therapistChatResponse, !isChatNotFound
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


    //updated endpoints bcz IDK what the hell I was thinking about when I wrote the previous ones XD
    @GetMapping("/allMessages")
    public ResponseEntity<ApiResponse<List<MessageResponseNew>>> getAllMessages() {
        try {
            // Retrieve all messages
            List<MessageResponseNew> messageListResponse = messageService.getAllMessages();

            //check if retrieval succeeded
            boolean isFail = messageListResponse == null || messageListResponse.isEmpty();

            //build response
            return apiResponseHelper.buildApiResponse(messageListResponse, !isFail
                    , (isFail ? "No message found for the user" : "Messages retrieved successfully")
                    , (isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            // Handle exceptions and return error response
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/allTherapistChats")
    public ResponseEntity<ApiResponse<List<TherapistChatResponse>>> getAllTherapistChats() {
        try {
            // Retrieve chats for the therapist
            List<TherapistChatResponse> therapistChatRespons = privateChatService.getAllTherapistChats();

            // Determine if no chats are found
            boolean isNoChatsFound = (therapistChatRespons == null || therapistChatRespons.isEmpty());

            //build response
            return apiResponseHelper.buildApiResponse(therapistChatRespons, !isNoChatsFound
                    , (isNoChatsFound ? "No chats found for the therapist" : "Chats retrieved successfully")
                    , (isNoChatsFound ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

}
//
