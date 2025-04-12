package juniverse.domain.mappers;

import juniverse.application.dtos.dashboard.TaskResponse;
import juniverse.domain.models.TaskModel;
import juniverse.persistance.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source="owner", target="owner.id")
    TaskEntity modelToEntity(TaskModel task);

    @Mapping(source="owner.id", target="owner")
    TaskModel entityToModel(TaskEntity taskEntity);

    TaskResponse modelToResponse(TaskModel taskModel);
}
