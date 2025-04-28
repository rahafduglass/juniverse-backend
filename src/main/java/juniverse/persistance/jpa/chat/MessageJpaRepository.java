package juniverse.persistance.jpa.chat;

import jakarta.transaction.Transactional;
import juniverse.domain.enums.ChatType;
import juniverse.domain.enums.MessageStatus;
import juniverse.persistance.entities.chat.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {

    @Query("SELECT m FROM message m WHERE m.privateChat.id = :privateChatId ORDER BY m.timestamp ASC")
    List<MessageEntity> findAllByPrivateChatId(Long privateChatId);

    @Query("SELECT COUNT(m) FROM message m WHERE m.privateChat.id = :chatId AND m.receiver.id= :receiverId AND m.isRead = false")
    Integer getNumOfUnreadMessagesByChatAndReceiver(@Param("chatId") Long chatId, @Param("receiverId") Long receiverId);


    @Modifying
    @Query("UPDATE message m SET m.isRead = true WHERE m.receiver.id = :receiverId AND m.privateChat.id = :privateChatId")
    Integer markMessagesAsRead(@Param("receiverId") Long receiverId, @Param("privateChatId") Long privateChatId);

    @Query("SELECT m FROM message m WHERE m.chatType = :chatType ORDER BY m.timestamp ASC")
    List<MessageEntity> findAllByChatType(ChatType chatType);

    @Transactional
    @Modifying
    @Query("UPDATE message m SET m.status = :messageStatus, m.deletedBy.id = :deletedBy WHERE m.id = :messageId")
    Integer updateByStatus(Long messageId, MessageStatus messageStatus,Long deletedBy);

    @Transactional
    @Modifying
    @Query("UPDATE message m SET m.content = :content WHERE m.id = :messageId")
    Integer updateByContent(Long messageId, String content);

    MessageEntity findSenderIdById(Long messageId);
}