package juniverse.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import juniverse.domain.enums.FileExtension;
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

    private LocalDateTime monitoredAt;

    private String name;

    private String path;

    private String size;

    private String description;

    private String status;

    private String type;

    private LocalDateTime uploadDate;

    private FileExtension extension;

    private Long folderEntityId;

    private Long monitoredById;

    private String monitoredByUsername;

    private Long ownerId;

    private String ownerUsername;
}
