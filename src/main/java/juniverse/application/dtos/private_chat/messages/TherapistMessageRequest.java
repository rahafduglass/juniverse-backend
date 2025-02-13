package juniverse.application.dtos.private_chat.messages;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TherapistMessageRequest {

    private Long privateChatId;

    private String receiverUsername;

    private String content;

}
