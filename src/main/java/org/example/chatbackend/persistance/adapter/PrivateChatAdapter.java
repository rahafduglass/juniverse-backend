package org.example.chatbackend.persistance.adapter;


import lombok.RequiredArgsConstructor;
import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.example.chatbackend.persistance.entities.PublicChatEntity;
import org.example.chatbackend.persistance.jpa.PrivateChatJpaRepository;
import org.example.chatbackend.persistance.repositories.PrivateChatRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PrivateChatAdapter implements PrivateChatRepository {

    private final PrivateChatJpaRepository privateChatJpaRepository;

    @Override
    public Long getPrivateChatIdByUserId(Long userId) {
        return privateChatJpaRepository.getIdBySenderId(userId);
    }
    @Override
    public PrivateChatEntity findById(long chatId) {
        return privateChatJpaRepository.findById(chatId);

    }
}
