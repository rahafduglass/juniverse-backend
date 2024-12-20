package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.persistance.entities.PublicChatEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicChatRepository {

    PublicChatEntity findById(long chatId);

    Long getPublicChatId();

}
