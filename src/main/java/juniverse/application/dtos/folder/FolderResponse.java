package juniverse.application.dtos.folder;

import juniverse.domain.enums.FolderStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FolderResponse {
    private Long id;

    private String name;

    private String description;

    private FolderStatus status;

    private Timestamp createdOn;

    private Timestamp modifiedOn;
}
