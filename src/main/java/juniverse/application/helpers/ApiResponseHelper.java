package juniverse.application.helpers;

import juniverse.application.dtos.ApiResponse;
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
