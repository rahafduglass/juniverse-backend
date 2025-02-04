package juniverse.chatbackend.persistance.repositories;

import juniverse.chatbackend.domain.models.SysUserModel;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    SysUserModel findUserById(Long id);
}
