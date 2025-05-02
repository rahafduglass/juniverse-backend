package juniverse.application.controllers.notification;

import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.file.FileResponse;
import juniverse.application.dtos.notifications.NotificationResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.notification.NotificationMapper;
import juniverse.domain.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {

    //todo
    // 1. clear notification
    // 2. mark as read
    // 3. get notifications

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;
    private final ApiResponseHelper apiResponseHelper;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotifications() {
        try{
            List<NotificationResponse> response= notificationService.getNotifications().stream().map(notificationMapper::modelToResponse).toList();

            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no files" : " retrieved successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
