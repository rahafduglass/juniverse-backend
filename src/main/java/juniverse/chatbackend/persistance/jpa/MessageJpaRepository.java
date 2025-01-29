package juniverse.chatbackend.persistance.jpa;

import juniverse.chatbackend.persistance.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByPrivateChatId(Long privateChatId);
}
