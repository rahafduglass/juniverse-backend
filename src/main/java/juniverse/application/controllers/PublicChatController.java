package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.private_chat.messages.UserMessageRequest;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.services.MessageService;
import juniverse.domain.services.PublicChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/public-chat")
@Tag(name = "PUBLIC CHAT")
public class PublicChatController {

    private final PublicChatService publicChatService;
    private final MessageService messageService;
    private final ApiResponseHelper apiResponseHelper;

    @PostMapping("/message")
    public ResponseEntity<ApiResponse<Boolean>> sendMessage(@RequestBody UserMessageRequest userMessageRequest) {
        try{
            boolean isSent= messageService.sendPublicMessage(userMessageRequest.getContent());
            return apiResponseHelper.buildApiResponse(isSent,isSent,!isSent?"couldn't send":"sent successfully",!isSent? HttpStatus.EXPECTATION_FAILED:HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

    }

}
