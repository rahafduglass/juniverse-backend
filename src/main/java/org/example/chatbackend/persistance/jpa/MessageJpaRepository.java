package org.example.chatbackend.persistance.jpa;

import org.example.chatbackend.persistance.entities.MessageEntity;
import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByPrivateChatId(Long privateChatId);
}
