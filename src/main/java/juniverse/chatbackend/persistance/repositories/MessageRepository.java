package juniverse.chatbackend.persistance.repositories;


import juniverse.chatbackend.domain.models.MessageModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository {

    Integer getNumOfUnreadMessagesByChatAndReceiver(Long chatId, Long receiverId);

    MessageModel sendMessage(MessageModel messageModel);

    List<MessageModel> findAllByPrivateChatId(Long id);

    Boolean markMessagesAsRead(Long userId, Long chatId);
}
