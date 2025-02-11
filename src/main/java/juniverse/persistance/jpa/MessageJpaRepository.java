package juniverse.persistance.jpa;

import juniverse.persistance.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByPrivateChatId(Long privateChatId);

    @Query("SELECT COUNT(m) FROM message m WHERE m.privateChat.id = :chatId AND m.receiver.id= :receiverId AND m.isRead = false")
    Integer getNumOfUnreadMessagesByChatAndReceiver(@Param("chatId") Long chatId, @Param("receiverId") Long receiverId);


    @Modifying
    @Query("UPDATE message m SET m.isRead = true WHERE m.receiver.id = :receiverId AND m.privateChat.id = :privateChatId")
    Integer markMessagesAsRead(@Param("receiverId") Long receiverId, @Param("privateChatId") Long privateChatId);

}

