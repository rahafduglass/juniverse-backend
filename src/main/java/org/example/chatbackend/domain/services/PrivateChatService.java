package org.example.chatbackend.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.PrivateChatMapper;
import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.persistance.repositories.PrivateChatRepository;
import org.example.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivateChatService {
    private final Long therapistId = 2L;
    private final PrivateChatRepository privateChatRepository;
    private final PrivateChatMapper privateChatMapper;
    private final UserRepository userRepository;

    public PrivateChatModel findPrivateChatByTherapistAndUser(Long senderId, Long receiverId) {
        return privateChatRepository.findPrivateChatByTherapistAndUser(userRepository.findUserById(senderId), userRepository.findUserById(receiverId));
    }
    public PrivateChatModel createPrivateChat(Long senderId) {
        PrivateChatModel privateChatTemp = new PrivateChatModel();
        privateChatTemp.setUserId(senderId);
        privateChatTemp.setTherapistId(therapistId);
        return privateChatRepository.createPrivateChat(privateChatTemp);
    }
}
