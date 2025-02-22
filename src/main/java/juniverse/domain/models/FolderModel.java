package juniverse.domain.models;

import juniverse.domain.enums.FolderStatus;
import juniverse.persistance.entities.SysUserEntity;
import lombok.Data;

@Data
public class FolderModel {

    private Long id;

    private String name;

    private String description;

    private String path;

    private FolderStatus status;

    private SysUserEntity createdBy;

}
