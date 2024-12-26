package org.example.chatbackend.domain.services;


import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.enums.ChatType;
import org.example.chatbackend.domain.models.MessageModel;
import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.persistance.repositories.MessageRepository;
import org.example.chatbackend.persistance.repositories.PrivateChatRepository;
import org.example.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final PrivateChatRepository privateChatRepository;
    private final UserRepository userRepository;
    private final PrivateChatService privateChatService; //this is not too good but yea lets keep it for now

    public MessageModel sendPrivateMessage(MessageModel messageModel) {
        //chat exists? send: create chat THEN send;
        PrivateChatModel privateChat;
        Long therapistId = 2L;
        if (messageModel.getSenderId().equals(therapistId)) {
            privateChat = privateChatRepository.findPrivateChatByUser(userRepository.findUserById(messageModel.getReceiverId()));
        } else {
            privateChat = privateChatRepository.findPrivateChatByUser(userRepository.findUserById(messageModel.getSenderId()));
            if (privateChat == null)
                privateChat = privateChatService.createPrivateChat(messageModel.getSenderId());
        }
        messageModel.setChatType(ChatType.PRIVATE);
        messageModel.setRead(false);
        messageModel.setTimestamp(LocalDateTime.now());
        messageModel.setPrivateChatId(privateChat.getId()); //this may cause a problemmmmmmmmm if it was null yayyy but nvm for now
        return sendMessage(messageModel);
    }

    public Long readMessage(Long id) {
        MessageModel messageModel = messageRepository.findById(id);
        messageModel.setRead(true);
        return messageRepository.updateMessageStatus(messageModel).getId();
    }

    private MessageModel sendMessage(MessageModel messageModel) {
        return messageRepository.sendMessage(messageModel);
    }


}

