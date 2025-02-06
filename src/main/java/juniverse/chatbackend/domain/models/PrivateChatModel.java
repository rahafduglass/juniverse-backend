package juniverse.chatbackend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChatModel {

    private Long id;

    private Long userId;

    private Long therapistId;

    private String userUsername;

    private String userFirstName;

    private String userLastName;

    private int userUnreadMessagesCount;

    private String therapistUsername;

    private String therapistFirstName;

    private String therapistLastName;

    private int therapistUnreadMessagesCount;

}
