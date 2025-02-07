package juniverse.chatbackend.domain.services;


import juniverse.chatbackend.domain.enums.ChatType;
import juniverse.chatbackend.domain.enums.MessageStatus;
import juniverse.chatbackend.domain.mappers.SysUserMapper;
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
    private final IdentityProvider identityProvider;

    private final SysUserMapper sysUserMapper;


    public MessageModel sendMessageToTherapist(String content) throws Exception {
        //method logic: chat doesn't exist? create chat THEN send: send;

        //check null message
        if (content.isEmpty())
            throw new Exception("can't send empty message");

        //retrieve current user
        SysUserEntity currentUser = identityProvider.currentIdentity();

        //retrieve therapist
        SysUserEntity therapist = sysUserRepository.findByUsername("omar_khaled").get(); //it's a static value not recommended, but we'll just use this bcz we have one therapist

        //retrieve user's chat
        PrivateChatModel privateChat = privateChatRepository.findByUser(sysUserMapper.entityToModel(currentUser));

        //check if it doesn't exist? create.
        if (privateChat == null) {
            privateChat = privateChatService.createChatBetween(currentUser.getId(), therapist.getId());
        }

        //set default & generated values
        MessageModel messageModel = new MessageModel();
        messageModel.setContent(content);
        messageModel.setSenderUsername(currentUser.getUsername());
        messageModel.setSenderId(currentUser.getId());
        messageModel.setReceiverUsername(therapist.getUsername());
        messageModel.setReceiverId(therapist.getId());
        messageModel.setChatType(ChatType.PRIVATE);
        messageModel.setIsRead(false);
        messageModel.setTimestamp(LocalDateTime.now());
        messageModel.setPrivateChatId(privateChat.getId());
        messageModel.setStatus(MessageStatus.SENT);

        //send message
        return sendMessage(messageModel);
    }

    private MessageModel sendMessage(MessageModel messageModel) {
        return messageRepository.sendMessage(messageModel);
    }

    public List<MessageModel> getAllMessages(Long chatId) {

        return  messageRepository.findAllByPrivateChatId(privateChatRepository.findById(chatId).getId());
    }

    public List<MessageModel> getAllMessages() {
        SysUserEntity currentUser = identityProvider.currentIdentity();
        return messageRepository.findAllByPrivateChatId(privateChatRepository.findByUser(currentUser).getId());
    }

    public MessageModel sendMessageFromTherapist(MessageModel messageModel) throws Exception {
        //method logic: chat doesn't exist? throw exception: send;

        //check null message
        if (messageModel.getContent().isEmpty())
            throw new Exception("can't send empty message");

        //retrieve therapist (the sender)
        SysUserEntity therapist = identityProvider.currentIdentity();

        //retrieve receiver
        SysUserEntity receiver;
        if (sysUserRepository.findByUsername(messageModel.getReceiverUsername()).isPresent())
            receiver = sysUserRepository.findByUsername(messageModel.getReceiverUsername()).get();
        else throw new Exception("receiver's username isn't valid");

        //retrieve user's chat
        PrivateChatModel privateChat = privateChatService.getChatById(messageModel.getPrivateChatId());

        //throw if chat doesn't exist
        if (privateChat == null) throw new Exception("private-chat not found");

        //set default & generated values
        messageModel.setSenderUsername(therapist.getUsername());
        messageModel.setSenderId(therapist.getId());
        messageModel.setReceiverUsername(receiver.getUsername());
        messageModel.setReceiverId(receiver.getId());
        messageModel.setChatType(ChatType.PRIVATE);
        messageModel.setIsRead(false);
        messageModel.setTimestamp(LocalDateTime.now());
        messageModel.setPrivateChatId(privateChat.getId());
        messageModel.setStatus(MessageStatus.SENT);

        //send message
        return sendMessage(messageModel);

    }

}