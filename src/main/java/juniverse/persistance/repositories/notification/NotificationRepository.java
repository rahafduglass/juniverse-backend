package juniverse.persistance.repositories.notification;

import juniverse.domain.models.notification.NotificationModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository {
    void createNotification(NotificationModel notificationModel);

    void saveAll(List<NotificationModel> notifications);

    List<NotificationModel> findAllByReceiverId(Long id);
}
