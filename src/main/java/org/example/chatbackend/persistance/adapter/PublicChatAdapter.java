package org.example.chatbackend.persistance.adapter;


import lombok.RequiredArgsConstructor;
import org.example.chatbackend.persistance.entities.PublicChatEntity;
import org.example.chatbackend.persistance.jpa.PublicChatJpaRepository;
import org.example.chatbackend.persistance.repositories.PublicChatRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PublicChatAdapter implements PublicChatRepository {

    private final PublicChatJpaRepository publicChatJpaRepository;
    @Override
    public PublicChatEntity findById(long chatId) {
        return publicChatJpaRepository.findById(chatId);
    }

    @Override
    public Long getPublicChatId() {
        return publicChatJpaRepository.getPublicChatId();
    }

}
