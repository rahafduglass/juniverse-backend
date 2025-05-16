package juniverse.application.controllers.notification;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.notifications.GetNotificationsResponse;
import juniverse.application.dtos.notifications.NotificationIdsRequest;
import juniverse.application.dtos.notifications.NotificationResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.notification.NotificationMapper;
import juniverse.domain.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="NOTIFICATIONS")
@RequestMapping("api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;
    private final ApiResponseHelper apiResponseHelper;

    @GetMapping()
    public ResponseEntity<ApiResponse<GetNotificationsResponse>> getNotifications() {
        try {
            List<NotificationResponse> allNotifications = notificationService.getNotifications().stream().map(notificationMapper::modelToResponse).toList();
            List<NotificationResponse> unreadMessages = allNotifications.stream()
                    .filter(n -> !n.getIsRead())
                    .toList();
            List<NotificationResponse> readNotifications = allNotifications.stream()
                    .filter(n -> n.getIsRead())
                    .toList();
            GetNotificationsResponse response = new GetNotificationsResponse();
            response.setReadNotifications(readNotifications);
            response.setNumberOfReadNotifications(readNotifications.size());
            response.setUnreadNotifications(unreadMessages);
            response.setNumberOfUnreadNotifications(unreadMessages.size());

            boolean isFail = allNotifications.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no notifications" : " retrieved successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/readAll")
    public ResponseEntity<ApiResponse<Boolean>> readAll(@RequestBody NotificationIdsRequest toReadNotifications) {
        try{
            boolean isFail= !notificationService.readAll(toReadNotifications.getNotificationIds());
            return apiResponseHelper.buildApiResponse(isFail, !isFail, isFail ? "couldn't update" : " updated successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch (Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Boolean>> read(@PathVariable Long notificationId) {
        try{
            boolean isFail= !notificationService.read(notificationId);
            return apiResponseHelper.buildApiResponse(isFail, !isFail, isFail ? "couldn't update" : " updated successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch (Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
