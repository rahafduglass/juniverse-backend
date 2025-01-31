package juniverse.chatbackend.application.dtos.private_chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChatResponse {

    private Long id;

    private Long userId;

    private Long therapistId;

    private String userFirstName;

    private String userLastName;

    private Integer unreadMessagesCount;


}
// user first and last name.
// number of unread messages in that chat
// last message in that chat
// the date of the last message
