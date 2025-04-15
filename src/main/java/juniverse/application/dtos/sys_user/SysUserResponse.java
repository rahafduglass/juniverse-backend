package juniverse.application.dtos.sys_user;

import juniverse.domain.enums.Major;
import juniverse.domain.enums.UserRole;
import lombok.Data;

@Data
public class SysUserResponse {
    private Long id;

    private String username;

    private Major major;

    private String email;

    private UserRole role;

    private String lastName;

    private String firstName;
}
