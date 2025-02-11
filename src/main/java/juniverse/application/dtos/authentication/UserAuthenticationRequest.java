package juniverse.application.dtos.authentication;


import lombok.Data;

@Data
public class UserAuthenticationRequest {
    private String username;
    private String password;
}
