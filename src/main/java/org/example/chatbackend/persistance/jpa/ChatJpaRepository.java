package org.example.chatbackend.persistance.jpa;

import org.example.chatbackend.persistance.entities.ChatEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatJpaRepository {

    ChatEntity findById(long chatId);
}
