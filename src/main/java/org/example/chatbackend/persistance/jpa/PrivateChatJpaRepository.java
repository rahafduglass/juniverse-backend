package org.example.chatbackend.persistance.jpa;

import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatJpaRepository {

    Long getIdBySenderId(Long userId);

    PrivateChatEntity findById(long chatId);
}
