package juniverse.chatbackend.persistance.repositories;

import juniverse.chatbackend.domain.models.UserModel;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    UserModel findUserById(Long id);
}
