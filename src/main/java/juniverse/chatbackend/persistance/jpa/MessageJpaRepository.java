package juniverse.chatbackend.persistance.jpa;

import juniverse.chatbackend.persistance.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByPrivateChatId(Long privateChatId);

    @Query("SELECT COUNT(m) FROM message m WHERE m.privateChat.id = :chatId AND m.receiver.id= :receiverId AND m.isRead = false")
    Integer getNumOfUnreadMessagesByChatIdAndReceiverId(@Param("chatId") Long chatId, @Param("receiverId") Long receiverId);


}

