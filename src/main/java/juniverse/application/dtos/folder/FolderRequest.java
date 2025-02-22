package juniverse.application.dtos.folder;

import juniverse.domain.enums.FolderStatus;
import juniverse.persistance.entities.SysUserEntity;
import lombok.Data;

@Data
public class FolderRequest {

    private String name;

    private String description;
    
}
