package juniverse.chatbackend.persistance.repositories;


import juniverse.chatbackend.domain.models.MessageModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository {

     Integer getNumOfUnreadMessagesByChatIdAndReceiverId(Long chatId,Long receiverId);

    MessageModel sendMessage(MessageModel messageModel);

    MessageModel updateMessageStatus(MessageModel messageModel);


    MessageModel findById(Long id);

    List<MessageModel> findAllByPrivateChatId(Long id);


    Boolean markMessagesAsRead(Long userId, Long chatId);
}
