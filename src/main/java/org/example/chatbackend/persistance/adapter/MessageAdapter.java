package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.GeneralChatMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.example.chatbackend.persistance.jpa.MessageJpaRepository;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageAdapter implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;
    private final GeneralChatMapper generalChatMapper;

    @Override
    public Message save(Message message) {
        MessageEntity messageEntity = generalChatMapper.modelToEntity(message);
        return (generalChatMapper.entityToModel(messageJpaRepository.save(messageEntity)));
    }
}
