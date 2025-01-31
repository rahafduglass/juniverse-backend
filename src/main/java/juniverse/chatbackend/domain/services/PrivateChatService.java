package juniverse.chatbackend.domain.services;

import juniverse.chatbackend.persistance.entities.PrivateChatEntity;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.application.dtos.private_chat.PrivateChatResponse;
import juniverse.chatbackend.application.dtos.private_message.MessageResponse;
import juniverse.chatbackend.domain.mappers.MessageMapper;
import juniverse.chatbackend.domain.mappers.PrivateChatMapper;
import juniverse.chatbackend.domain.models.MessageModel;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.persistance.repositories.MessageRepository;
import juniverse.chatbackend.persistance.repositories.PrivateChatRepository;
import juniverse.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateChatService {
    private final PrivateChatRepository privateChatRepository;
    private final PrivateChatMapper privateChatMapper;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final SysUserService sysUserService;

    public PrivateChatModel createPrivateChat(Long senderId) {
        PrivateChatModel privateChatTemp = new PrivateChatModel();
        privateChatTemp.setUserId(senderId);
        Long therapistId = 2L;
        privateChatTemp.setTherapistId(therapistId);
        return privateChatRepository.createPrivateChat(privateChatTemp);
    }

    public List<MessageResponse> getUserChatMessages(Long userId) {
        PrivateChatModel privateChatModel = privateChatRepository.findPrivateChatByUser(userRepository.findUserById(userId));
        List<MessageModel> listOfMessages = messageRepository.findAllByPrivateChatId(privateChatModel.getId());
        return messageMapper.listOfModelsToListOfResponses(listOfMessages);

    }

    public List<PrivateChatResponse> getTherapistChats(Long therapistId) {
        List<Object[]> results = privateChatRepository.findAllByTherapistId(therapistId);
        return results.stream().map(row -> {
            PrivateChatEntity chat = (PrivateChatEntity) row[0];  // Extract PrivateChatEntity
            SysUserEntity user = (SysUserEntity) row[1];  // Extract SysUserEntity

            //mapping chat & user to dto
            PrivateChatResponse privateChatResponse = privateChatMapper.entityToResponse(chat, user);

            //manually map unreadMessagesCount -- fix this and map it inside the mapper
            privateChatResponse.setUnreadMessagesCount(messageRepository.getNumOfUnreadMessagesByChatIdAndReceiverId(chat.getId(), therapistId));
            return privateChatResponse;
        }).collect(Collectors.toList());
        //map list of

    }


    public PrivateChatResponse getPrivateChatById(Long chatId) {
        PrivateChatEntity chat = privateChatRepository.findPrivateChatById(chatId);
        if (chat == null) {
            return null;
        }
        return privateChatMapper.entityToResponse(chat, chat.getUser());
    }


}
