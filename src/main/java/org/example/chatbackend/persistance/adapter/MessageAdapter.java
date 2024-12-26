package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.MessageModel;
import org.example.chatbackend.persistance.jpa.MessageJpaRepository;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageAdapter implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageModel sendMessage(MessageModel messageModel) {
        return messageMapper.entityToModel(messageJpaRepository.save(messageMapper.modelToEntity(messageModel)));
    }

    @Override
    public MessageModel updateMessageStatus(MessageModel messageModel) {
        return messageMapper.entityToModel(messageJpaRepository.save(messageMapper.modelToEntity(messageModel)));
    }

    @Override
    public MessageModel findById(Long id) {
        return messageMapper.entityToModel(messageJpaRepository.findById(id).get());
    }

    @Override
    public List<MessageModel> findAllByPrivateChatId(Long chatId) {
        return messageMapper.listOfEntitiesToListOfModels(messageJpaRepository.findAllByPrivateChatId(chatId));
    }
}