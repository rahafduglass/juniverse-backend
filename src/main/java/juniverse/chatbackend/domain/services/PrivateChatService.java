package juniverse.chatbackend.domain.services;

import juniverse.chatbackend.application.dtos.private_chat.TherapistChatResponse;
import juniverse.chatbackend.domain.mappers.PrivateChatMapper;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.domain.provider.IdentityProvider;
import juniverse.chatbackend.persistance.entities.PrivateChatEntity;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.repositories.MessageRepository;
import juniverse.chatbackend.persistance.repositories.PrivateChatRepository;
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




    public PrivateChatModel createPrivateChat(Long senderId) {
        PrivateChatModel privateChatTemp = new PrivateChatModel();
        privateChatTemp.setUserId(senderId);
        Long therapistId = 2L;
        privateChatTemp.setTherapistId(therapistId);
        return privateChatRepository.createPrivateChat(privateChatTemp);
    }


    public TherapistChatResponse getPrivateChatById(Long chatId) {
        PrivateChatEntity chat = privateChatRepository.findPrivateChatById(chatId);
        if (chat == null) {
            return null;
        }
        return privateChatMapper.entityToResponse(chat, chat.getUser());
    }


    public Boolean markMessagesAsRead(Long userId, Long chatId) {
        return messageRepository.markMessagesAsRead(userId, chatId);
    }


    public List<TherapistChatResponse> getAllTherapistChats() {

        //retrieve therapist details
        SysUserEntity sysUserEntity = identityProvider.currentIdentity();

        //retrieve all chats
        List<Object[]> results = privateChatRepository.findAllByTherapistId(sysUserEntity.getId());

        //extracting then mapping results(entities) to (DTO(response)) --- > SHOULD BE FIXED to (models)
        return results.stream().map(row -> {

            // Extract PrivateChatEntity from list row[0]
            PrivateChatEntity chat = (PrivateChatEntity) row[0];

            // Extract SysUserEntity from list row[1]
            SysUserEntity user = (SysUserEntity) row[1];

            //mapping
            TherapistChatResponse therapistChatResponse = privateChatMapper.entityToResponse(chat, user);

            //retrieve and manually map unreadMessagesCount ---> FIND A BETTER WAY
            therapistChatResponse.setUnreadMessagesCount(messageRepository.getNumOfUnreadMessagesByChatAndReceiver(chat.getId(), sysUserEntity.getId()));

            //return sing response to be collected in List<Response>
            return therapistChatResponse;
        }).collect(Collectors.toList());
    }
}
