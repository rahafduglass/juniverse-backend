package juniverse.persistance.repositories.notification;

import juniverse.domain.models.notification.NotificationModel;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository {
    void createNotification(NotificationModel notificationModel);

}
