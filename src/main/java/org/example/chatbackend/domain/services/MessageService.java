package org.example.chatbackend.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.enums.MessageStatus;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


    public Message sendPublicMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message sendPrivateMessage(Message message, Long therapistId) {
        return messageRepository.save(message);
    }



    public List<Message> getAllPublicMessages(Long chatId) {
        return messageRepository.findAllPublicMessagesByChatIdAndMessageStatus(chatId, MessageStatus.SENT);
    }


    public List<Message> getAllPrivateMessages(Long chatId) {
        return messageRepository.findAllPublicMessagesByChatIdAndMessageStatus(chatId, MessageStatus.SENT);
    }
}
