package juniverse.domain.services.notification;

import juniverse.domain.models.notification.NotificationModel;
import juniverse.persistance.repositories.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createNotification(NotificationModel notificationModel){
        notificationRepository.createNotification(notificationModel);
    }


}
