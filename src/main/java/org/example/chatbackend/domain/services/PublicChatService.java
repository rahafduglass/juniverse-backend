package org.example.chatbackend.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.persistance.repositories.PublicChatRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicChatService {
    private final PublicChatRepository publicChatRepository;

    public Long getPublicChatId() {
        return publicChatRepository.getPublicChatId();
    }

}
