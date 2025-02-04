package juniverse.chatbackend.application.dtos;


import juniverse.chatbackend.domain.enums.UserRole;
import lombok.Data;


@Data
public class RegisterRequest {
    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private UserRole role;



}