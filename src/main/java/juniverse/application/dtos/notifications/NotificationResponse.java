package juniverse.application.dtos.notifications;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;

    private String content;

    private Boolean isRead;

    private Long receiverId;

    private LocalDateTime createdOn;

    private String type;
}
