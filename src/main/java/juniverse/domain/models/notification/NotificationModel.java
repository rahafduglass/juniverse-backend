package juniverse.domain.models.notification;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationModel {
    private Long id;

    private String content;

    private Boolean isRead;

    private Long receiverId;

    private LocalDateTime createdOn;

    private String type;

    private Long folderId;

}
