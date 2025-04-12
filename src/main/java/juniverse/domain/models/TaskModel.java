package juniverse.domain.models;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import juniverse.persistance.entities.SysUserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskModel {

    private Long id;

    private String title;

    private boolean isChecked;

    private Long owner;
}
