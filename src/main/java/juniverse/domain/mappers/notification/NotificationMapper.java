package juniverse.domain.mappers.notification;

import juniverse.domain.models.notification.NotificationModel;
import juniverse.persistance.entities.notification.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

     @Mapping(source="receiverId",target="receiver.id")
     NotificationEntity modelToEntity(NotificationModel notificationModel);
}
