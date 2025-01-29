package juniverse.chatbackend.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import juniverse.chatbackend.domain.enums.UserRole;

@Entity (name="sys_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_user")
public class SysUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
