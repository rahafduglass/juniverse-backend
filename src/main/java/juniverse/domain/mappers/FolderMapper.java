package juniverse.domain.mappers;


import juniverse.application.dtos.folder.FolderRequest;
import juniverse.domain.models.FolderModel;
import juniverse.persistance.entities.FolderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    FolderModel requestToModel(FolderRequest folderRequest);

    FolderEntity modelToEntity(FolderModel folderModel);

    FolderModel entityToModel(FolderEntity entity);
}
