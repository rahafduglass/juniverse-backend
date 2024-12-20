package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatRepository {

    Long getPrivateChatIdByUserId(Long userId);
    PrivateChatEntity findById(long chatId);
}
