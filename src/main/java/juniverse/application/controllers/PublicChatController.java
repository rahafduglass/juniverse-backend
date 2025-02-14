package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.chats.UserMessageRequest;
import juniverse.application.dtos.chats.public_chat.MessageResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.MessageMapper;
import juniverse.domain.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/public-chat")
@Tag(name = "PUBLIC CHAT")
public class PublicChatController {

    private final MessageService messageService;
    private final ApiResponseHelper apiResponseHelper;
    private final MessageMapper messageMapper;

    @PostMapping("/message")
    public ResponseEntity<ApiResponse<Boolean>> sendMessage(@RequestBody UserMessageRequest userMessageRequest) {
        try {
            boolean isSent = messageService.sendPublicMessage(userMessageRequest.getContent());
            return apiResponseHelper.buildApiResponse(isSent, isSent, !isSent ? "couldn't send" : "sent successfully", !isSent ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getAllMessages() {
        try {
            List<MessageResponse> responses = messageMapper.listOfModelsToListOfResponses( messageService.getAllPublicMessages());
            return apiResponseHelper.buildApiResponse(responses, responses!=null, responses==null ? "couldn't retrieve" : "retrieved successfully", responses==null ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping ("/{messageId}")
    public ResponseEntity<ApiResponse<Boolean>> markMessageAsDeleted(@PathVariable Long messageId) {
        try{
            boolean isDeleted= messageService.deleteMessage(messageId);
            return apiResponseHelper.buildApiResponse(isDeleted, isDeleted, !isDeleted? "couldn't find message" : "marked as deleted successfully", !isDeleted ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<ApiResponse<Boolean>> editMessage(@PathVariable Long messageId,@RequestBody UserMessageRequest request) {
        try{
            boolean isEdited=messageService.editMessage(messageId,request.getContent());
            return apiResponseHelper.buildApiResponse(isEdited,isEdited,!isEdited?"couldn't find message":"successfully edited",!isEdited ? HttpStatus.NOT_FOUND : HttpStatus.OK);

        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
