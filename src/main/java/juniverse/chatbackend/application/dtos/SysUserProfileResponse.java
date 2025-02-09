package juniverse.chatbackend.application.dtos;

import juniverse.chatbackend.domain.enums.Major;
import juniverse.chatbackend.domain.enums.UserRole;
import lombok.Data;

@Data
public class SysUserProfileResponse {

    private String firstName;

    private String lastName;

    private String username;

    private Major major;

    private UserRole role;

    private String bio;
}
