package juniverse.persistance.adapter;

import jakarta.transaction.Transactional;
import juniverse.domain.enums.ChatType;
import juniverse.domain.enums.MessageStatus;
import juniverse.domain.mappers.MessageMapper;
import juniverse.domain.models.MessageModel;
import juniverse.persistance.jpa.MessageJpaRepository;
import juniverse.persistance.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageAdapter implements MessageRepository {


    private final MessageJpaRepository messageJpaRepository;
    private final MessageMapper messageMapper;


    @Override
    public Integer getNumOfUnreadMessagesByChatAndReceiver(Long chatId, Long receiverId) {
        return messageJpaRepository.getNumOfUnreadMessagesByChatAndReceiver(chatId,receiverId);
    }


    @Override
    public MessageModel sendMessage(MessageModel messageModel) {
        return messageMapper.entityToModel(messageJpaRepository.save(messageMapper.modelToEntity(messageModel)));
    }


    @Override
    public List<MessageModel> findAllByPrivateChatId(Long chatId) {
        return messageMapper.listOfEntitiesToListOfModels(messageJpaRepository.findAllByPrivateChatId(chatId));
    }


    @Transactional
    @Override
    public void markMessagesAsRead(Long userId, Long chatId) {
        messageJpaRepository.markMessagesAsRead(userId, chatId);
    }


    @Override
    public List<MessageModel> findAllByChatType(ChatType chatType) {
        return messageMapper.listOfEntitiesToListOfModels(messageJpaRepository.findAllByChatType(chatType));
    }

    @Override
    public boolean deleteMessage(Long messageId) {
        return messageJpaRepository.updateByStatus(messageId, MessageStatus.DELETED)>0;
    }
}