package juniverse.persistance.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import juniverse.domain.enums.FolderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "folder")
public class FolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Column(name = "name", nullable = false)
    private String name;


    @NotNull
    @Column(name = "path", nullable = false)
    private String path;


    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private FolderStatus status;



    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private SysUserEntity createdBy;

}