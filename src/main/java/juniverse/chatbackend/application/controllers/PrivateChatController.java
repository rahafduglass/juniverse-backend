package juniverse.chatbackend.application.controllers;

import juniverse.chatbackend.application.dtos.ApiResponse;
import juniverse.chatbackend.application.dtos.private_chat.TherapistChatResponse;
import juniverse.chatbackend.application.dtos.private_chat.UserChatResponse;
import juniverse.chatbackend.application.dtos.private_chat.messages.MessageRequest;
import juniverse.chatbackend.application.dtos.private_chat.messages.MessageResponse;
import juniverse.chatbackend.application.helpers.ApiResponseHelper;
import juniverse.chatbackend.domain.mappers.MessageMapper;
import juniverse.chatbackend.domain.mappers.PrivateChatMapper;
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
    private final PrivateChatMapper privateChatMapper;


    @GetMapping("/{chatId}/allMessages")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getAllMessages(@PathVariable Long chatId) {
        try {
            // Retrieve all messages
            List<MessageResponse> messageListResponse = messageService.getAllMessages(chatId);

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

    @GetMapping("/allMessages")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getAllMessages() {
        try {
            // Retrieve all messages
            List<MessageResponse> messageListResponse = messageService.getAllMessages();

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
            List<TherapistChatResponse> therapistChatResponse = privateChatService.getAllTherapistChats();

            // Determine if no chats are found
            boolean isNoChatsFound = (therapistChatResponse == null || therapistChatResponse.isEmpty());

            //build response
            return apiResponseHelper.buildApiResponse(therapistChatResponse, !isNoChatsFound
                    , (isNoChatsFound ? "No chats found for the therapist" : "Chats retrieved successfully")
                    , (isNoChatsFound ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<UserChatResponse>> getChat() {
        try {
            //retrieve chat
            UserChatResponse userChatResponse = privateChatMapper.modelToUserChatResponse(privateChatService.getChat());

            //build api response
            if (userChatResponse != null) {
                return apiResponseHelper.buildApiResponse(userChatResponse, true, "User chat retrieved successfully", HttpStatus.OK);
            } else return apiResponseHelper.buildApiResponse(null, false, "No user chat found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/messageFromTherapist")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessageFromTherapist(@RequestBody MessageRequest messageRequest) {
        try{
            MessageResponse messageResponse= messageMapper.modelToResponse(messageService.sendMessageFromTherapist(messageMapper.requestToModel(messageRequest)));
            if(messageResponse != null) {
                return apiResponseHelper.buildApiResponse(messageResponse, true,"message sent successfully", HttpStatus.OK);
            }else return apiResponseHelper.buildApiResponse(null, false, "failed to send", HttpStatus.EXPECTATION_FAILED);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/messageToTherapist")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessageToTherapist(@RequestBody String content) {
        try {
            //send message
            MessageResponse messageResponse = messageMapper.modelToResponse(messageService.sendMessageToTherapist(content));
            //build api response
            if (messageResponse != null) {
                return apiResponseHelper.buildApiResponse(messageResponse, true, "Message sent successfully", HttpStatus.OK);
            } else
                return apiResponseHelper.buildApiResponse(null, false, "failed to send", HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PatchMapping("/{chatId}/read")
    public ResponseEntity<ApiResponse<Boolean>> markChatAsRead(@PathVariable Long chatId) {
        try {
            //mark As Read
            Boolean isRead = privateChatService.marChatAsRead(chatId);
            return apiResponseHelper.buildApiResponse(isRead, isRead, !isRead ? "failed to read" : "Chat read successfully", !isRead ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
