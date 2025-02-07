package juniverse.chatbackend.persistance.repositories;

import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysUserRepository {
    SysUserModel findById(Long id);

    Optional<SysUserEntity> findByUsername(String username);

    List<SysUserEntity> saveAll(List<SysUserEntity> users);
}
