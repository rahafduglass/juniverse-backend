package juniverse.domain.models.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskModel {

    private Long id;

    private String title;

    private Boolean isChecked;

    private Long owner;
}
