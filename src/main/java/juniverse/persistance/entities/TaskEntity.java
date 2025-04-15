package juniverse.persistance.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "task")
@Data
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Boolean isChecked=false;

    @ManyToOne
    @NotNull
    private SysUserEntity owner;

}
