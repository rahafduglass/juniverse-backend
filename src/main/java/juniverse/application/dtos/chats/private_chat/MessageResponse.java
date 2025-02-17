package juniverse.application.dtos.chats.private_chat;

import juniverse.domain.enums.ChatType;
import juniverse.domain.enums.MessageStatus;
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

    private String senderUsername;

    private MessageStatus status;

    private ChatType chatType;

    private String receiverUsername;

    private Long privateChatId;

    private Boolean isRead;

}
