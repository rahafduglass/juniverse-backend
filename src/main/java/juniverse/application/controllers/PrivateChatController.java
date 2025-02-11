package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.private_chat.TherapistChatResponse;
import juniverse.application.dtos.private_chat.UserChatResponse;
import juniverse.application.dtos.private_chat.messages.MessageRequest;
import juniverse.application.dtos.private_chat.messages.TherapistMessageResponse;
import juniverse.application.dtos.private_chat.messages.UserMessageRequest;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.MessageMapper;
import juniverse.domain.mappers.PrivateChatMapper;
import juniverse.domain.services.MessageService;
import juniverse.domain.services.PrivateChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/private-chat")
@Tag(name = "PRIVATE CHAT")
public class PrivateChatController {

    private final PrivateChatService privateChatService;
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ApiResponseHelper apiResponseHelper;
    private final PrivateChatMapper privateChatMapper;


    @GetMapping("/{chatId}/allMessages")
    public ResponseEntity<ApiResponse<List<TherapistMessageResponse>>> getAllMessages(@PathVariable Long chatId) {
        try {
            // Retrieve all messages
            List<TherapistMessageResponse> messageListResponse = messageMapper.listOfModelsToListOfResponses(messageService.getAllMessages(chatId));

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
    public ResponseEntity<ApiResponse<List<TherapistMessageResponse>>> getAllMessages() {
        try {
            // Retrieve all messages
            List<TherapistMessageResponse> messageListResponse = messageMapper.listOfModelsToListOfResponses(messageService.getAllMessages());

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
            List<TherapistChatResponse> therapistChatResponse = privateChatMapper.listOfModelsToListOfResponses(privateChatService.getAllTherapistChats());

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
    public ResponseEntity<ApiResponse<TherapistMessageResponse>> sendMessageFromTherapist(@RequestBody MessageRequest messageRequest) {
        try {
            TherapistMessageResponse therapistMessageResponse = messageMapper.modelToResponse(messageService.sendMessageFromTherapist(messageMapper.requestToModel(messageRequest)));
            if (therapistMessageResponse != null) {
                return apiResponseHelper.buildApiResponse(therapistMessageResponse, true, "message sent successfully", HttpStatus.OK);
            } else
                return apiResponseHelper.buildApiResponse(null, false, "failed to send", HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/messageToTherapist")
    public ResponseEntity<ApiResponse<TherapistMessageResponse>> sendMessageToTherapist(@RequestBody UserMessageRequest userMessageRequest) {
        try {
            //send message
            TherapistMessageResponse therapistMessageResponse = messageMapper.modelToResponse(messageService.sendMessageToTherapist(userMessageRequest.getContent()));
            //build api response
            if (therapistMessageResponse != null) {
                return apiResponseHelper.buildApiResponse(therapistMessageResponse, true, "Message sent successfully", HttpStatus.OK);
            } else
                return apiResponseHelper.buildApiResponse(null, false, "failed to send", HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    //TO-DO
    //make markAsRead implicitly implemented within getAllMessages
    //NEW endpoint: deleteMessage

}