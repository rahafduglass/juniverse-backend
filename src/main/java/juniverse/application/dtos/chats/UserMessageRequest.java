package juniverse.application.dtos.chats;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserMessageRequest {
    String content;
}
