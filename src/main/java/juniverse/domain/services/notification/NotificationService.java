package juniverse.domain.services.notification;

import juniverse.domain.models.notification.NotificationModel;
import juniverse.domain.models.user.SysUserModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.notification.NotificationRepository;
import juniverse.persistance.repositories.user.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final IdentityProvider identityProvider;
    private final SysUserRepository sysUserRepository;

    public void createNotification(NotificationModel notificationModel) {
        notificationRepository.createNotification(notificationModel);
    }

    public void notifyAllUsers(NotificationModel notificationModel) {
        List<SysUserModel> allUsers = sysUserRepository.findAll();
        List<NotificationModel> notifications = new ArrayList<>();
        for (SysUserModel user : allUsers) {
            NotificationModel notification = NotificationModel.builder()
                    .createdOn(notificationModel.getCreatedOn())
                    .type(notificationModel.getType())
                    .receiverId(user.getId())
                    .content(notificationModel.getContent())
                    .isRead(notificationModel.getIsRead())
                    .build();
            notifications.add(notification);
        }
        notificationRepository.saveAll(notifications);
    }

    public List<NotificationModel> getNotifications() {
        return notificationRepository.findAllByReceiverId(identityProvider.currentIdentity().getId());
    }


}
