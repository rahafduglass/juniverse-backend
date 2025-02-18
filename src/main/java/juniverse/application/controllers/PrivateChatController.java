package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.chats.UserMessageRequest;
import juniverse.application.dtos.chats.private_chat.MessageResponse;
import juniverse.application.dtos.chats.private_chat.TherapistChatResponse;
import juniverse.application.dtos.chats.private_chat.TherapistMessageRequest;
import juniverse.application.dtos.chats.private_chat.UserChatResponse;
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
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getAllMessages(@PathVariable Long chatId) {
        try {
            List<MessageResponse> therapistMessages = messageMapper.listOfModelsToListOfTherapistResponses(messageService.getAllPrivateMessages(chatId));
            boolean isFail = therapistMessages == null || therapistMessages.isEmpty();

            return apiResponseHelper.buildApiResponse(therapistMessages, !isFail
                    , (isFail ? "No message found for the user" : "Messages retrieved successfully")
                    , (isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/allMessages")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getAllMessages() {
        try {
            List<MessageResponse> userMessages = messageMapper.listOfModelsToListOfTherapistResponses(messageService.getAllPrivateMessages());
            boolean isFail = userMessages == null || userMessages.isEmpty();

            return apiResponseHelper.buildApiResponse(userMessages, !isFail
                    , (isFail ? "No message found for the user" : "Messages retrieved successfully")
                    , (isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK));

        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/allTherapistChats")
    public ResponseEntity<ApiResponse<List<TherapistChatResponse>>> getAllTherapistChats() {
        try {

            List<TherapistChatResponse> therapistChatResponse = privateChatMapper.listOfModelsToListOfResponses(privateChatService.getAllTherapistChats());
            boolean isNoChatsFound = (therapistChatResponse == null || therapistChatResponse.isEmpty());

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
            UserChatResponse userChatResponse = privateChatMapper.modelToUserChatResponse(privateChatService.getChat());
            boolean isFail = userChatResponse == null;

            return apiResponseHelper.buildApiResponse(userChatResponse, isFail, isFail ? "No user chat found" : "chat retrieved successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/messageFromTherapist")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessageFromTherapist(@RequestBody TherapistMessageRequest therapistMessageRequest) {
        try {
            MessageResponse messageResponse = messageMapper.modelToResponse(messageService.sendMessageFromTherapist(therapistMessageRequest.getContent(), therapistMessageRequest.getReceiverUsername(), therapistMessageRequest.getPrivateChatId()));
            boolean isFail = messageResponse == null;

            return apiResponseHelper.buildApiResponse(messageResponse, isFail, isFail ? "failed to send" : "message sent successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/messageToTherapist")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessageToTherapist(@RequestBody UserMessageRequest userMessageRequest) {
        try {
            MessageResponse messageResponse = messageMapper.modelToResponse(messageService.sendMessageToTherapist(userMessageRequest.getContent()));
            boolean isFail = messageResponse == null;

            return apiResponseHelper.buildApiResponse(messageResponse, isFail, isFail ? "failed to send" : "message sent successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<ApiResponse<Boolean>> editMessage(@PathVariable Long messageId, @RequestBody UserMessageRequest request) {
        try {
            boolean isEdited = messageService.editMessage(messageId, request.getContent());
            return apiResponseHelper.buildApiResponse(isEdited, isEdited, !isEdited ? "couldn't find message" : "successfully edited", !isEdited ? HttpStatus.NOT_FOUND : HttpStatus.OK);

        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

}