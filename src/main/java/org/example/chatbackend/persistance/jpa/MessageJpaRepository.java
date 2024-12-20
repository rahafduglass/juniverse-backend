package org.example.chatbackend.persistance.jpa;

import org.example.chatbackend.domain.enums.MessageStatus;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.entities.ChatEntity;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity,Long> {
    List<Message> findAllByChatIdAndMessageStatus(ChatEntity chatId, MessageStatus messageStatus);
}
