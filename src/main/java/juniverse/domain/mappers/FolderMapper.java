package juniverse.domain.mappers;


import juniverse.application.dtos.folder.FolderRequest;
import juniverse.application.dtos.folder.FolderResponse;
import juniverse.domain.models.FolderModel;
import juniverse.persistance.entities.FolderEntity;
import juniverse.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    FolderModel requestToModel(FolderRequest folderRequest);

    @Mapping(source="createdBy",target="createdBy")
    @Mapping(source="modifiedBy",target="modifiedBy")
    FolderEntity modelToEntity(FolderModel folderModel);

    @Mapping(source="createdBy.id",target="createdBy")
    @Mapping(source="modifiedBy.id",target="modifiedBy")
    FolderModel entityToModel(FolderEntity entity);



    default SysUserEntity mapSysUserIdToSysUserEntity(Long sysUserId) {
        if ( sysUserId == null ) {
            return null;
        }

        SysUserEntity sysUserEntity = new SysUserEntity();

        sysUserEntity.setId( sysUserId );

        return sysUserEntity;
    }


    List<FolderModel> listOfEntitiesToListOfModels(List<FolderEntity> folders);

    List<FolderResponse> listOfModelsToListOfResponses(List<FolderModel> folders);
}
