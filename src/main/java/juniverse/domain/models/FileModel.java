package juniverse.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import juniverse.domain.enums.FileExtension;
import juniverse.domain.enums.FileStatus;
import juniverse.persistance.entities.FolderEntity;
import juniverse.persistance.entities.SysUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileModel {

    private Long id;

    private String name;

    private String path;

    private String size;

    private String description;

    private FileStatus status;

    private LocalDateTime uploadDate;

    private FileExtension extension;

    private Long folderId;

    private String folderName;

    private LocalDateTime monitoredAt;

    private Long monitoredById;

    private String monitoredByUsername;

    private Long ownerId;

    private String ownerUsername;
}
