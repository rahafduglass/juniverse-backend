package juniverse.chatbackend.application.dtos.private_message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private Long id;

    private String content;

    private LocalDateTime timestamp;

    private Long senderId;

    private Long receiverId;

    private Long privateChatId;

    private Boolean isRead;

}
