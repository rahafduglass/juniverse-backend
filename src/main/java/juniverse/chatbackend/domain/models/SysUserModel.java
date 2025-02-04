package juniverse.chatbackend.domain.models;

import jakarta.persistence.*;
import juniverse.chatbackend.domain.enums.ChatType;
import juniverse.chatbackend.domain.enums.Major;
import juniverse.chatbackend.domain.enums.MessageStatus;
import juniverse.chatbackend.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserModel {

    private Long id;

    private String password;

    private String firstName;

    private String username;

    private Major major;

    private String lastName;

    private String email;

    private UserRole role;

    private String profilePicture;

    private String coverPicture;

    private String bio;
}
