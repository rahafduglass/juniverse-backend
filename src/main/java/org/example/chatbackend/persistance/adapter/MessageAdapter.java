package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.MessageModel;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.example.chatbackend.persistance.jpa.MessageJpaRepository;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.example.chatbackend.persistance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageAdapter implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;

    private final MessageMapper messageMapper;
    private final UserRepository userRepository;

    @Override
    public MessageModel sendMessage(MessageModel messageModel) {
        return messageMapper.entityToModel(messageJpaRepository.save(messageMapper.modelToEntity(messageModel)));
    }
}
