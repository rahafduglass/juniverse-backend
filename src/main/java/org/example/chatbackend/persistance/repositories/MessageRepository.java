package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.domain.enums.MessageStatus;
import org.example.chatbackend.domain.models.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository {

    Message save(Message message);

    List<Message> findAllPublicMessagesByChatIdAndMessageStatus(long chatId, MessageStatus sent);
    List<Message> findAllPrivateMessagesByChatIdAndMessageStatus(long chatId, MessageStatus sent);
}
