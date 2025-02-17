package juniverse.domain.services;

import juniverse.domain.mappers.PrivateChatMapper;
import juniverse.domain.models.PrivateChatModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.entities.PrivateChatEntity;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.repositories.MessageRepository;
import juniverse.persistance.repositories.PrivateChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateChatService {

    private final PrivateChatRepository privateChatRepository;
    private final PrivateChatMapper privateChatMapper;
    private final MessageRepository messageRepository;
    private final IdentityProvider identityProvider;


    public PrivateChatModel createChatBetween(Long userId, Long therapistId) {

        PrivateChatModel privateChatModel = new PrivateChatModel();
        privateChatModel.setUserId(userId);
        privateChatModel.setTherapistId(therapistId);

        return privateChatRepository.create(privateChatModel);
    }

    public List<PrivateChatModel> getAllTherapistChats() {

        Long therapistId = identityProvider.currentIdentity().getId();
        List<Object[]> therapistChats = privateChatRepository.findAllByTherapistId(therapistId);

        return therapistChats.stream().map(row -> {

            PrivateChatEntity chatEntity = (PrivateChatEntity) row[0];
            PrivateChatModel chatModel = privateChatMapper.entityToModel(chatEntity);

            chatModel.setTherapistUnreadMessagesCount(messageRepository.getNumOfUnreadMessagesByChatAndReceiver(chatEntity.getId(), therapistId));

            return chatModel;
        }).collect(Collectors.toList());
    }

    public PrivateChatModel getChat() {

        Long currentUserId = identityProvider.currentIdentity().getId();
        PrivateChatModel userPrivateChat = privateChatRepository.findByUser(currentUserId);

        if (userPrivateChat == null) throw new RuntimeException("private-chat not found");

        userPrivateChat.setUserUnreadMessagesCount(messageRepository.getNumOfUnreadMessagesByChatAndReceiver(userPrivateChat.getId(), currentUserId));

        return userPrivateChat;
    }

    public void markChatAsRead(Long chatId) {
        messageRepository.markMessagesAsRead(identityProvider.currentIdentity().getId(), chatId);
    }

    public PrivateChatModel getChatById(Long privateChatId) {
        return privateChatRepository.findById(privateChatId);
    }
}
