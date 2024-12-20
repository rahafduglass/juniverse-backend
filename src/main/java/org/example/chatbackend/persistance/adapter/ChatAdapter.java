package org.example.chatbackend.persistance.adapter;


import lombok.RequiredArgsConstructor;
import org.example.chatbackend.persistance.entities.ChatEntity;
import org.example.chatbackend.persistance.jpa.ChatJpaRepository;
import org.example.chatbackend.persistance.repositories.ChatRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatAdapter implements ChatRepository {
    private final ChatJpaRepository chatJpaRepository;

    @Override
    public ChatEntity findById(long chatId) {
        return chatJpaRepository.findById(chatId);
    }
}
