package juniverse.domain.models;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationModel {

    private  String username;
    private  String password;
    private  String token;
}
