package juniverse.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monitored_at")
    private Instant monitoredAt;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "path", nullable = false)
    private String path;


    @Column(name = "size", nullable = false)
    private String size;


    @Column(name = "status", nullable = false)
    private String status;


    @Column(name = "type", nullable = false)
    private String type;


    @Column(name = "upload_date", nullable = false)
    private Instant uploadDate;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "folder_id")
    private FolderEntity folderEntity;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "monitored_by_id")
    private SysUserEntity monitoredBy;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private SysUserEntity owner;

}