package juniverse.domain.models.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TherapistNoteModel {

    private Long id;

    private String title;

    private String description;

    private Long privateChatId;

    private Long therapistId;
}
