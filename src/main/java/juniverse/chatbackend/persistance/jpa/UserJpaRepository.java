package juniverse.chatbackend.persistance.jpa;

import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<SysUserEntity, Long> {
    Optional<SysUserEntity> findByUsername(String username);
}
