package org.example.chatbackend.persistance.jpa;

import org.example.chatbackend.persistance.entities.PublicChatEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicChatJpaRepository {

    PublicChatEntity findById(long chatId);

    @Query("SELECT c.id FROM PublicChatEntity c WHERE c.chatType = 'PUBLIC'") // this may cause a problem if there was more than one record but we trusting we have only one private chat
    Long getPublicChatId();
}
