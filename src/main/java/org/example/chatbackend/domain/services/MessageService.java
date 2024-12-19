package org.example.chatbackend.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.GeneralChatMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final GeneralChatMapper generalChatMapper;

    public Message sendMessage(Message message) {

        return messageRepository.save(message);
    }


}
