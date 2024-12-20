package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.persistance.entities.ChatEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository {

    ChatEntity findById(long chatId);

}
