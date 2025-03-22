package juniverse.application.dtos.note;

import lombok.Data;

@Data
public class NoteResponse {

    private Long id;

    private String title;

    private String description;

    private Long privateChatId;

    private Long therapistId;
}
