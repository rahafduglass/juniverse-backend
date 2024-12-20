package org.example.chatbackend.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.enums.MessageStatus;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }


    public List<Message> getAllMessages(Long id) {
        return messageRepository.findAllByChatIdAndMessageStatus(id, MessageStatus.SENT);
    }
}
