package juniverse.persistance.adapter.chat;

import jakarta.transaction.Transactional;
import juniverse.domain.enums.ChatType;
import juniverse.domain.enums.MessageStatus;
import juniverse.domain.mappers.chat.MessageMapper;
import juniverse.domain.models.chat.MessageModel;
import juniverse.persistance.jpa.chat.MessageJpaRepository;
import juniverse.persistance.repositories.chat.MessageRepository;
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
    public boolean deleteMessage(Long messageId, Long deletedBy) {
        return messageJpaRepository.updateByStatus(messageId, MessageStatus.DELETED,deletedBy)>0;
    }

    @Override
    public boolean updateMessageContent(Long messageId, String content) {
        return messageJpaRepository.updateByContent(messageId,content)>0;
    }

    @Override
    public Long findSenderId(Long messageId) {
        return messageJpaRepository.findSenderIdById(messageId).getSender().getId();
    }

    @Override
    public MessageModel findById(Long messageId) {
        return messageMapper.entityToModel(messageJpaRepository.findById(messageId).get());
    }

    @Override
    public Long getNumOfUnreadMessagesByReceiverId(Long receiverId) {
        return messageJpaRepository.getNumOfUnreadMessagesByReceiverId(receiverId);
    }
}