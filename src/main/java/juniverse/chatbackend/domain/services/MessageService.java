package juniverse.chatbackend.domain.services;


import juniverse.chatbackend.application.dtos.private_message.MessageResponseNew;
import juniverse.chatbackend.domain.enums.ChatType;
import juniverse.chatbackend.domain.enums.MessageStatus;
import juniverse.chatbackend.domain.mappers.MessageMapper;
import juniverse.chatbackend.domain.models.MessageModel;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.domain.provider.IdentityProvider;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.repositories.MessageRepository;
import juniverse.chatbackend.persistance.repositories.PrivateChatRepository;
import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {


    private final MessageRepository messageRepository;
    private final PrivateChatRepository privateChatRepository;
    private final SysUserRepository sysUserRepository;
    private final PrivateChatService privateChatService;
    private final MessageMapper messageMapper;
    private final IdentityProvider identityProvider;


    public MessageModel sendPrivateMessage(MessageModel messageModel) {


        //chat exists? send: create chat THEN send;
        PrivateChatModel privateChat;
        Long therapistId = 2L;

        //retrieve chat using userId in a user-therapist chat
        if (messageModel.getSenderId().equals(therapistId)) {
            privateChat = privateChatRepository.findPrivateChatByUser(sysUserRepository.findUserById(messageModel.getReceiverId()));
        } else {
            privateChat = privateChatRepository.findPrivateChatByUser(sysUserRepository.findUserById(messageModel.getSenderId()));
        }

        //check if it doesn't exist? create.
        if (privateChat == null)
            privateChat = privateChatService.createPrivateChat(messageModel.getSenderId());

        //set default values
        messageModel.setChatType(ChatType.PRIVATE);
        messageModel.setIsRead(false);
        messageModel.setTimestamp(LocalDateTime.now());
        messageModel.setPrivateChatId(privateChat.getId());
        messageModel.setStatus(MessageStatus.SENT);

        return sendMessage(messageModel);
    }

    private MessageModel sendMessage(MessageModel messageModel) {
        return messageRepository.sendMessage(messageModel);
    }

    public List<MessageResponseNew> getAllMessages() {
        SysUserEntity sysUserEntity=  identityProvider.currentIdentity();
        SysUserEntity currentUser=sysUserRepository.findByUsername(sysUserEntity.getUsername()).get();

        PrivateChatModel privateChatModel = privateChatRepository.findByUser(currentUser);
        List<MessageModel> listOfMessages = messageRepository.findAllByPrivateChatId(privateChatModel.getId());
        return messageMapper.listOfModelsToListOfResponsesNew(listOfMessages);
    }
}

