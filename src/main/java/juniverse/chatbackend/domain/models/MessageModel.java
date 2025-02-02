package juniverse.chatbackend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import juniverse.chatbackend.domain.enums.ChatType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {

    private Long id;

    private String content;

    private LocalDateTime timestamp;

    private ChatType chatType; // Enum for chat type: PUBLIC or PRIVATE

    private Long senderId; //always needed either a user to public OR user to therapist OR therapist to user

    private Long receiverId; //nullable for public chat

    private Long privateChatId; //nullable for public chat

    private Boolean isRead;
}
