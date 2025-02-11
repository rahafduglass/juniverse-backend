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
        PrivateChatModel privateChatTemp = new PrivateChatModel();
        privateChatTemp.setUserId(userId);
        privateChatTemp.setTherapistId(therapistId);
        return privateChatRepository.create(privateChatTemp);
    }
    //replace response
    public List<PrivateChatModel> getAllTherapistChats() {

        //retrieve therapist details
        SysUserEntity sysUserEntity = identityProvider.currentIdentity();

        //retrieve all chats
        List<Object[]> results = privateChatRepository.findAllByTherapistId(sysUserEntity.getId());

        //preparing and mapping results
        return results.stream().map(row -> {

            // Extract PrivateChatEntity from list row[0]
            PrivateChatEntity chat = (PrivateChatEntity) row[0];

            //mapping
            PrivateChatModel privateChatModel= privateChatMapper.entityToModel(chat);

            //retrieve and manually map unreadMessagesCount
            privateChatModel.setTherapistUnreadMessagesCount(messageRepository.getNumOfUnreadMessagesByChatAndReceiver(chat.getId(), sysUserEntity.getId()));

            //return model to be collected in List<Models>
            return privateChatModel;
        }).collect(Collectors.toList());
    }

    public PrivateChatModel getChat() {
        //retrieve current user
        SysUserEntity currentUser = identityProvider.currentIdentity();

        //retrieve private chat using current user
        PrivateChatEntity chat = privateChatRepository.findByUser(currentUser);

        //check if not null
        if (chat == null) throw new RuntimeException("private-chat not found");

        //privateChatModel
        PrivateChatModel privateChatModel=privateChatMapper.entityToModel(chat);

        //retrieve and manually map unreadMessagesCount ---> FIND A BETTER WAY
        privateChatModel.setUserUnreadMessagesCount(messageRepository.getNumOfUnreadMessagesByChatAndReceiver(chat.getId(), currentUser.getId()));

        //mapping entity to our model
        return privateChatModel;
    }

    public void markChatAsRead(Long chatId) {
        messageRepository.markMessagesAsRead(identityProvider.currentIdentity().getId(), chatId);
    }

    public PrivateChatModel getChatById(Long privateChatId) {
        return privateChatMapper.entityToModel(privateChatRepository.findById(privateChatId));
    }
}
