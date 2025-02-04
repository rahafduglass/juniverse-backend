package juniverse.chatbackend.domain.services;


import juniverse.chatbackend.domain.enums.MessageStatus;
import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.enums.ChatType;
import juniverse.chatbackend.domain.models.MessageModel;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.persistance.repositories.MessageRepository;
import juniverse.chatbackend.persistance.repositories.PrivateChatRepository;
import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {


    private final MessageRepository messageRepository;
    private final PrivateChatRepository privateChatRepository;
    private final SysUserRepository sysUserRepository;
    private final PrivateChatService privateChatService;


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

    public Long readMessage(Long id) {
        MessageModel messageModel = messageRepository.findById(id);
        messageModel.setIsRead(true);
        return messageRepository.updateMessageStatus(messageModel).getId();
    }

    private MessageModel sendMessage(MessageModel messageModel) {
        return messageRepository.sendMessage(messageModel);
    }


}

