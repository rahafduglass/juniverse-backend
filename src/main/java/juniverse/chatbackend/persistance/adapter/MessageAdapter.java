package juniverse.chatbackend.persistance.adapter;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.mappers.MessageMapper;
import juniverse.chatbackend.domain.models.MessageModel;
import juniverse.chatbackend.persistance.jpa.MessageJpaRepository;
import juniverse.chatbackend.persistance.repositories.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageAdapter implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;
    private final MessageMapper messageMapper;

    @Override
    public Integer getNumOfUnreadMessagesByChatIdAndReceiverId(Long chatId,Long receiverId) {
        return messageJpaRepository.getNumOfUnreadMessagesByChatIdAndReceiverId(chatId,receiverId);
    }

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

    @Transactional
    @Override
    public Boolean markMessagesAsRead(Long userId, Long chatId) {
        return messageJpaRepository.markMessagesAsRead(userId,chatId)>0;
    }
}