package juniverse.application.dtos.private_chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChatResponse {

    private Long id;

    private String therapistUsername;

    private String therapistFirstName;

    private String therapistLastName;

    private Integer userUnreadMessagesCount;

}
