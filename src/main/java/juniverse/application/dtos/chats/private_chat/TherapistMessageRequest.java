package juniverse.application.dtos.chats.private_chat;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TherapistMessageRequest {

    private Long privateChatId;

    private String receiverUsername;

    private String content;

}
