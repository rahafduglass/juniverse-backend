package juniverse.application.dtos.notifications;

import lombok.Data;

import java.util.List;

@Data
public class NotificationIdsRequest {
    private List<Long> notificationIds;
}
