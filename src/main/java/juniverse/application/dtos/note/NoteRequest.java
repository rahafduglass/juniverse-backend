package juniverse.application.dtos.note;

import lombok.Data;

@Data
public class NoteRequest {

    private String title;

    private String description;

    private Long privateChatId;

}
