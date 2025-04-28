package juniverse.domain.mappers.news;

import juniverse.application.dtos.event.EventRequest;
import juniverse.application.dtos.event.EventResponse;
import juniverse.domain.models.news.EventModel;
import juniverse.persistance.entities.news.EventEntity;
import juniverse.persistance.entities.user.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventModel requestToModel(EventRequest eventRequest);

    @Mapping(source="createdById",target="createdBy")
    EventEntity modelToEntity(EventModel eventModel);

    default SysUserEntity mapSysUserIdToEntity(Long sysUserId) {
        if (sysUserId == null) {
            return null;  // Return null if there's no privateChatId
        }

        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(sysUserId);

        return sysUserEntity;
    }

    EventResponse modelToResponse(EventModel eventModel);

    List<EventModel> entityToModel(List<EventEntity> eventEntities);
}
