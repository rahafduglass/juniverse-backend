package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository {
    Message save(Message message);
}
