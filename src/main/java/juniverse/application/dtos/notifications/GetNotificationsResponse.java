package juniverse.application.dtos.notifications;


import lombok.Data;

import java.util.List;

@Data
public class GetNotificationsResponse {
    private int numberOfUnreadNotifications;

    private List<NotificationResponse> unreadNotifications;

    private int numberOfReadNotifications;

    private List<NotificationResponse> readNotifications;
}
