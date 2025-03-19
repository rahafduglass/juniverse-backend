package juniverse.application.dtos.file;

import juniverse.domain.enums.FileExtension;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileResponse {

    private Long id;

    private String name;

    private String description;

    private FileExtension extension;

    private LocalDateTime uploadDate;

    private Long folderId;

    private String folderName;

    private String ownerUsername;

    private LocalDateTime monitoredAt;

    private String monitoredByUsername;

}
