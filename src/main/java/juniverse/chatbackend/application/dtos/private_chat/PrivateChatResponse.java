package juniverse.chatbackend.application.dtos.private_chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChatResponse {

    private Long id;

    private Long userId;

    private Long therapistId;
}
