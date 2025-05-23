package juniverse.persistance.adapter.notification;

import juniverse.domain.mappers.notification.NotificationMapper;
import juniverse.domain.models.notification.NotificationModel;
import juniverse.persistance.entities.notification.NotificationEntity;
import juniverse.persistance.jpa.notification.NotificationJpaRepository;
import juniverse.persistance.repositories.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationAdapter implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void createNotification(NotificationModel notificationModel) {
        notificationJpaRepository.save(notificationMapper.modelToEntity(notificationModel));
    }

    @Override
    public void saveAll(List<NotificationModel> notifications) {
        List<NotificationEntity> notificationEntities = notifications.stream().map(notificationMapper::modelToEntity).toList();
        List<NotificationEntity> entities = notificationJpaRepository.saveAll(notificationEntities);
    }

    @Override
    public List<NotificationModel> findAllByReceiverId(Long receiverId) {
        List<NotificationModel> notifications = new ArrayList<>();
        notifications.addAll(notificationJpaRepository.findAllReadNotificationsByReceiverId(receiverId, PageRequest.of(0, 7))
                .stream()
                .map(notificationMapper::entityToModel)
                .toList()
        );
        notifications.addAll(notificationJpaRepository.findAllUnReadNotificationsByReceiverId(receiverId)
                .stream()
                .map(notificationMapper::entityToModel)
                .toList()
        );
        return notifications;
    }

    @Override
    public boolean updateNotificationsAsRead(List<Long> toReadNotifications) {
        return notificationJpaRepository.updateAsRead(toReadNotifications) > 0;
    }
}
