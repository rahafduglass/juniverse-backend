package juniverse.chatbackend.application.helpers;

import juniverse.chatbackend.application.dtos.ApiResponse;
import juniverse.chatbackend.application.dtos.private_message.MessageResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Configuration
public class ApiResponseHelper {

    public <T> ResponseEntity<ApiResponse<T>> buildApiResponse(T response, boolean success, String message, HttpStatus status) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .success(success)
                .message(message)
                .data(response)
                .build();
        return ResponseEntity.status(status).body(apiResponse);
    }
}
