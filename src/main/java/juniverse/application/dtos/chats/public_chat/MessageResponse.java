package juniverse.application.dtos.chats.public_chat;

import juniverse.domain.enums.MessageStatus;
import juniverse.domain.enums.UserRole;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class MessageResponse {

    private Long id;

    private String content;

    private LocalDateTime timestamp;

    private MessageStatus status;

    private String senderUsername;

    private UserRole senderRole;


}
