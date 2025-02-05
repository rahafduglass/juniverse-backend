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
}
