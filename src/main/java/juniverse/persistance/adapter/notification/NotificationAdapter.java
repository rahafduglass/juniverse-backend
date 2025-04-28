package juniverse.persistance.adapter.notification;

import juniverse.domain.mappers.notification.NotificationMapper;
import juniverse.domain.models.notification.NotificationModel;
import juniverse.persistance.jpa.notification.NotificationJpaRepository;
import juniverse.persistance.repositories.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void createNotification(NotificationModel notificationModel) {
        notificationJpaRepository.save(notificationMapper.modelToEntity(notificationModel));
    }
}
