package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.enums.MessageStatus;
import org.example.chatbackend.domain.mappers.MessageMapper;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.example.chatbackend.persistance.entities.PublicChatEntity;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.example.chatbackend.persistance.jpa.MessageJpaRepository;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageAdapter implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;
    private final PublicChatAdapter publicChatAdapter;
    private final PrivateChatAdapter privateChatAdapter;
    private final MessageMapper messageMapper;

    @Override
    public Message save(Message message) {
        MessageEntity messageEntity = messageMapper.modelToEntity(message);
        return (messageMapper.entityToModel(messageJpaRepository.save(messageEntity)));
    }

    @Override
    public List<Message> findAllPublicMessagesByChatIdAndMessageStatus(long chatId, MessageStatus messageStatus) {
        PublicChatEntity publicChatEntity= publicChatAdapter.findById(chatId);
        return messageJpaRepository.findAllByChatIdAndMessageStatus(publicChatEntity,messageStatus);
    }

    @Override
    public List<Message> findAllPrivateMessagesByChatIdAndMessageStatus(long chatId, MessageStatus messageStatus) {
        PrivateChatEntity privateChatEntity= privateChatAdapter.findById(chatId);
        return messageJpaRepository.findAllByChatIdAndMessageStatus(privateChatEntity,messageStatus);
    }
}
