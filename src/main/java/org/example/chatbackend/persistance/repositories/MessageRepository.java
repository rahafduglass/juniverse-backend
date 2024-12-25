package org.example.chatbackend.persistance.repositories;


import org.example.chatbackend.domain.models.MessageModel;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository {

    MessageModel sendMessage(MessageModel messageModel);
}
