package org.example.chatbackend.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.private_chat.PrivateChatResponse;
import org.example.chatbackend.application.dtos.private_message.MessageResponse;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.mappers.PrivateChatMapper;
import org.example.chatbackend.domain.models.MessageModel;
import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.example.chatbackend.persistance.repositories.PrivateChatRepository;
import org.example.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateChatService {
    private final PrivateChatRepository privateChatRepository;
    private final PrivateChatMapper privateChatMapper;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public PrivateChatModel createPrivateChat(Long senderId) {
        PrivateChatModel privateChatTemp = new PrivateChatModel();
        privateChatTemp.setUserId(senderId);
        Long therapistId = 2L;
        privateChatTemp.setTherapistId(therapistId);
        return privateChatRepository.createPrivateChat(privateChatTemp);
    }

    public List<MessageResponse> getUserChatMessages(Long userId) {
        PrivateChatModel privateChatModel = privateChatRepository.findPrivateChatByUser(userRepository.findUserById(userId));
        List<MessageModel> listOfMessages = messageRepository.findAllByPrivateChatId(privateChatModel.getId());
        return messageMapper.listOfModelsToListOfResponses(listOfMessages);

    }

    public List<PrivateChatResponse> getTherapistChats(Long therapistId) {
        return privateChatMapper.listOfModelsToListOfResponses(privateChatRepository.findAllByTherapistId(therapistId));
    }
}
