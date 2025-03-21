package juniverse.persistance.entities;

import jakarta.persistence.*;
import juniverse.domain.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import juniverse.domain.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity (name="sys_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_user")
public class SysUserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String firstName;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Major major;

    @Column( nullable=false)
    private String lastName;

    @Column(unique=true, nullable=false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private UserRole role;

    private String profilePicturePath;
    private String profilePictureExtension;

    private String coverPicturePath;
    private String coverPictureExtension;

    private String bio;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
