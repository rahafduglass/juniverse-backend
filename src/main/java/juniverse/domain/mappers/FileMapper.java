package juniverse.domain.mappers;

import juniverse.application.dtos.file.FileRequest;
import juniverse.domain.models.FileModel;
import juniverse.persistance.entities.FileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface FileMapper {

    FileModel requestToModel(FileRequest fileRequest);

    FileEntity modelToEntity(FileModel fileModel);

    FileModel entityToModel(FileEntity fileEntity);
}
