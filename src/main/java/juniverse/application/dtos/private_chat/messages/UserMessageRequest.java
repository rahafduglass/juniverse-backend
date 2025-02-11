package juniverse.application.dtos.private_chat.messages;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserMessageRequest {
    String content;
}
