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

    private LocalDateTime time;

    private Long receiverId;

}
