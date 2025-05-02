package juniverse.domain.mappers.notification;

import juniverse.application.dtos.notifications.NotificationResponse;
import juniverse.domain.models.notification.NotificationModel;
import juniverse.persistance.entities.notification.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

     @Mapping(source="receiverId",target="receiver.id")
     NotificationEntity modelToEntity(NotificationModel notificationModel);

     @Mapping(source="receiver.id",target="receiverId")
     NotificationModel entityToModel(NotificationEntity notificationEntity);

     NotificationResponse modelToResponse(NotificationModel notificationModel);
}
