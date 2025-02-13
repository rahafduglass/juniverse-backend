package juniverse.domain.services;


import juniverse.domain.enums.ChatType;
import juniverse.domain.enums.MessageStatus;
import juniverse.domain.mappers.PrivateChatMapper;
import juniverse.domain.models.MessageModel;
import juniverse.domain.models.PrivateChatModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.repositories.MessageRepository;
import juniverse.persistance.repositories.PrivateChatRepository;
import juniverse.persistance.repositories.SysUserRepository;
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
    private final PrivateChatMapper privateChatMapper;


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
        PrivateChatModel privateChat = privateChatMapper.entityToModel(privateChatRepository.findByUserId(currentUser.getId()));
        System.out.println(privateChat + "dddddddddddddddddddd");
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

    public List<MessageModel> getAllPrivateMessages(Long chatId) {
        List<MessageModel> data = messageRepository.findAllByPrivateChatId(privateChatRepository.findById(chatId).getId());
        privateChatService.markChatAsRead(chatId);
        return data;
    }

    public List<MessageModel> getAllPrivateMessages() {
        Long chatId = privateChatRepository.findByUser(identityProvider.currentIdentity()).getId();
        List<MessageModel> data = messageRepository.findAllByPrivateChatId(chatId);
        privateChatService.markChatAsRead(chatId);
        return data;
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

    public boolean sendPublicMessage(String content) {

        MessageModel messageModel = new MessageModel();
        messageModel.setContent(content);
        SysUserEntity currentUser = identityProvider.currentIdentity();
        messageModel.setSenderUsername(currentUser.getUsername());
        messageModel.setSenderId(currentUser.getId());
        messageModel.setChatType(ChatType.PUBLIC);
        messageModel.setStatus(MessageStatus.SENT);
        messageModel.setTimestamp(LocalDateTime.now());
        messageModel.setPrivateChatId(null);
        messageModel.setReceiverId(null);
        messageModel.setReceiverUsername(null);
        messageModel.setIsRead(null);

        return sendMessage(messageModel) != null;
    }

    private MessageModel sendMessage(MessageModel messageModel) {
        return messageRepository.sendMessage(messageModel);
    }

    public List<MessageModel> getAllPublicMessages() {
        return messageRepository.findAllByChatType(ChatType.PUBLIC);
    }

    public boolean deleteMessage(Long messageId) {
        return messageRepository.deleteMessage(messageId);
    }

    public boolean editMessage(Long messageId, String content) throws Exception {
        boolean isAuthorized = (identityProvider.currentIdentity().getId()==messageRepository.findSenderId(messageId));

        if (isAuthorized)
            return messageRepository.updateMessageContent(messageId, content);
        else
            throw new Exception("can't edit, message doesn't belong to the user");
    }
}