package juniverse.domain.models;

import juniverse.domain.enums.FolderStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FolderModel {

    private Long id;

    private String name;

    private String description;

    private String path;

    private FolderStatus status;

    private Long createdBy;

    private Timestamp createdOn;

    private  Long ModifiedBy;

    private Timestamp modifiedOn;
}
