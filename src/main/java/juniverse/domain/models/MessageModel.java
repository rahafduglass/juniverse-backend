package juniverse.domain.models;

import juniverse.domain.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import juniverse.domain.enums.ChatType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {

    private Long id;

    private String content;

    private LocalDateTime timestamp;

    private ChatType chatType;

    private MessageStatus status;

    private String senderUsername;

    private String receiverUsername;

    private Long receiverId;

    private Long senderId;

    private Long privateChatId;

    private Long publicChatId;

    private Boolean isRead;

}
