package juniverse.chatbackend.persistance.adapter;

import jakarta.transaction.Transactional;
import juniverse.chatbackend.domain.mappers.MessageMapper;
import juniverse.chatbackend.domain.models.MessageModel;
import juniverse.chatbackend.persistance.jpa.MessageJpaRepository;
import juniverse.chatbackend.persistance.repositories.MessageRepository;
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
    public Boolean markMessagesAsRead(Long userId, Long chatId) {
        return messageJpaRepository.markMessagesAsRead(userId,chatId)>0;
    }
}