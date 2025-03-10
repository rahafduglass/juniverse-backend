package juniverse.domain.mappers;

import juniverse.application.dtos.file.FileRequest;
import juniverse.domain.models.FileModel;
import juniverse.persistance.entities.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface FileMapper {

    FileModel requestToModel(FileRequest fileRequest);

    @Mapping(source="folderId", target="folder.id")
    @Mapping(source="ownerId", target="owner.id")
    FileEntity modelToEntity(FileModel fileModel);

    @Mapping(source="owner.id", target="ownerId")
    @Mapping(source="folder.id", target="folderId")
    FileModel entityToModel(FileEntity fileEntity);
}
