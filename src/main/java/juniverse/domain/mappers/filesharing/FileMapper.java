package juniverse.domain.mappers.filesharing;

import juniverse.application.dtos.file.FileRequest;
import juniverse.application.dtos.file.FileResponse;
import juniverse.domain.models.filesharing.FileModel;
import juniverse.persistance.entities.filesharing.FileEntity;
import juniverse.persistance.entities.user.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface FileMapper {

    FileModel requestToModel(FileRequest fileRequest);

    @Mapping(source="folderId", target="folder.id")
    @Mapping(source="ownerId", target="owner")
    @Mapping(source="monitoredById", target="monitoredBy")
    FileEntity modelToEntity(FileModel fileModel);

    @Mapping(source="owner.id", target="ownerId")
    @Mapping(source="folder.id", target="folderId")
    @Mapping(source="owner.username", target="ownerUsername")
    @Mapping(source="monitoredBy.username", target="monitoredByUsername")
    @Mapping(source="monitoredBy.id", target="monitoredById")
    @Mapping(source="folder.name", target="folderName")
    FileModel entityToModel(FileEntity fileEntity);

    FileResponse modelToResponse(FileModel element);

    default SysUserEntity mapSysUserIdToEntity(Long sysUserId) {
        if (sysUserId == null) {
            return null;  // Return null if there's no privateChatId
        }

        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(sysUserId);

        return sysUserEntity;
    }
}
