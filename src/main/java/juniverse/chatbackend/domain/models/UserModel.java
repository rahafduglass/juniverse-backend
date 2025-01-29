package juniverse.chatbackend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import juniverse.chatbackend.domain.enums.UserRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private UserRole role;
}
