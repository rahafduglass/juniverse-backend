package juniverse.application.dtos.dashboard;

import lombok.Data;

@Data
public class TaskResponse {
    private int id;
    private String title;
    private Boolean isChecked;
}
