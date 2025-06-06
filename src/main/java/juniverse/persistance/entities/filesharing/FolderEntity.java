package juniverse.persistance.entities.filesharing;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import juniverse.domain.enums.FolderStatus;
import juniverse.persistance.entities.user.SysUserEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "folder")
public class FolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Column(name = "name", nullable = false,unique = true)
    private String name;

    @NotNull
    private String description;


    private String path;


    @Enumerated(EnumType.STRING)
    private FolderStatus status;


    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private SysUserEntity createdBy;

    private Timestamp createdOn;

    @ManyToOne(fetch = FetchType.EAGER)
    private SysUserEntity modifiedBy;

    private Timestamp modifiedOn;

    @OneToMany(mappedBy="folder",cascade = CascadeType.REMOVE)
    private List<FileEntity> files = new ArrayList<>();
}