package org.example.chatbackend.domain.services;


import lombok.RequiredArgsConstructor;
import org.example.chatbackend.persistance.repositories.PrivateChatRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrivateChatService {

    private final PrivateChatRepository privateChatRepository;

    public Long getPrivateChatIdByUserId(Long userId) {
        return privateChatRepository.getPrivateChatIdByUserId(userId);
    }


}
