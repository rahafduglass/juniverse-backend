package juniverse.chatbackend.application.dtos;


import juniverse.chatbackend.domain.enums.Major;
import juniverse.chatbackend.domain.enums.UserRole;
import lombok.Data;


@Data
public class RegisterRequest {
    private String username;

    private String password;

    private String email;

    private Major major;

    private String firstName;

    private String lastName;

    private UserRole role;

    private String profilePicturePath;

    private String coverPicturePath;

    private String bio;


}