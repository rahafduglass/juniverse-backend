package juniverse.persistance.repositories;


import juniverse.domain.enums.ChatType;
import juniverse.domain.models.MessageModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository {

    Integer getNumOfUnreadMessagesByChatAndReceiver(Long chatId, Long receiverId);

    MessageModel sendMessage(MessageModel messageModel);

    List<MessageModel> findAllByPrivateChatId(Long id);

    void markMessagesAsRead(Long userId, Long chatId);

    List<MessageModel> findAllByChatType(ChatType chatType);

    boolean deleteMessage(Long messageId);

    boolean updateMessageContent(Long messageId, String content);

    Long findSenderId(Long messageId);
}
