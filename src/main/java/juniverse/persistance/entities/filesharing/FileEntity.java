package juniverse.persistance.entities.filesharing;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import juniverse.domain.enums.FileExtension;
import juniverse.domain.enums.FileStatus;
import juniverse.persistance.entities.user.SysUserEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name="file")
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monitored_at")
    private LocalDateTime monitoredAt;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "path", nullable = false)
    private String path;


    @NotNull
    private String description;

    @Column(name = "status", nullable = false)
    private FileStatus status;


    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private FileExtension extension;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "folder_id")
    @NotNull
    private FolderEntity folder;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "monitored_by_id")
    private SysUserEntity monitoredBy;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private SysUserEntity owner;

}