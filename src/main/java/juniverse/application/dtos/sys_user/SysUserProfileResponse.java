package juniverse.application.dtos.sys_user;

import juniverse.domain.enums.Major;
import juniverse.domain.enums.UserRole;
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
