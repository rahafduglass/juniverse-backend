package juniverse.chatbackend.persistance.repositories;

import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserRepository {
    SysUserModel findUserById(Long id);

    Optional<SysUserEntity> findByUsername(String username);
}
