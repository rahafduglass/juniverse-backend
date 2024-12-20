package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.enums.MessageStatus;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.entities.ChatEntity;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.example.chatbackend.persistance.jpa.MessageJpaRepository;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageAdapter implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;
    private final ChatAdapter chatAdapter;
    private final MessageMapper chatMapper;

    @Override
    public Message save(Message message) {
        MessageEntity messageEntity = chatMapper.modelToEntity(message);
        return (chatMapper.entityToModel(messageJpaRepository.save(messageEntity)));
    }

    @Override
    public List<Message> findAllByChatIdAndMessageStatus(long chatId, MessageStatus messageStatus) {
        ChatEntity chatEntity=chatAdapter.findById(chatId);
        return messageJpaRepository.findAllByChatIdAndMessageStatus(chatEntity,messageStatus);
    }

}
