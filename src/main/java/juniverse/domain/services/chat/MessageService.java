package juniverse.domain.services.chat;


import juniverse.domain.enums.ChatType;
import juniverse.domain.enums.MessageStatus;
import juniverse.domain.enums.UserRole;
import juniverse.domain.mappers.notification.NotificationMapper;
import juniverse.domain.models.chat.MessageModel;
import juniverse.domain.models.chat.PrivateChatModel;
import juniverse.domain.models.filesharing.FileModel;
import juniverse.domain.models.notification.NotificationModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.domain.services.filesharing.FileService;
import juniverse.domain.services.notification.NotificationService;
import juniverse.persistance.entities.user.SysUserEntity;
import juniverse.persistance.repositories.chat.MessageRepository;
import juniverse.persistance.repositories.chat.PrivateChatRepository;
import juniverse.persistance.repositories.user.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageService {


    private final MessageRepository messageRepository;
    private final PrivateChatRepository privateChatRepository;
    private final SysUserRepository sysUserRepository;
    private final PrivateChatService privateChatService;
    private final IdentityProvider identityProvider;
    private final NotificationService notificationService;
    private final FileService fileService;

    public MessageModel sendMessageToTherapist(String content) throws Exception {

        if (content.isEmpty())
            throw new Exception("can't send empty message");

        SysUserEntity currentUser = identityProvider.currentIdentity();
        SysUserEntity therapist = sysUserRepository.findByUsername("omar_khaled").get();
        PrivateChatModel privateChat = privateChatRepository.findByUserId(currentUser.getId());

        if (privateChat == null) {
            privateChat = privateChatService.createChatBetween(currentUser.getId(), therapist.getId());
        }

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


        return sendMessage(messageModel);
    }

    public List<MessageModel> getAllPrivateMessages(Long chatId) {
        List<MessageModel> privateMessages = messageRepository.findAllByPrivateChatId(privateChatRepository.findById(chatId).getId());
        privateChatService.markChatAsRead(chatId);
        return privateMessages;
    }

    public List<MessageModel> getAllPrivateMessages() {
        Long chatId = privateChatRepository.findByUser(identityProvider.currentIdentity()).getId();
        List<MessageModel> privateMessages = messageRepository.findAllByPrivateChatId(chatId);

        privateChatService.markChatAsRead(chatId);

        return privateMessages;
    }

    public MessageModel sendMessageFromTherapist(String content, String receiverUsername, Long privateChatId) throws Exception {

        if (content.isEmpty())
            throw new Exception("can't send empty message");

        SysUserEntity therapist = identityProvider.currentIdentity();
        SysUserEntity receiver;
        if (sysUserRepository.findByUsername(receiverUsername).isPresent())
            receiver = sysUserRepository.findByUsername(receiverUsername).get();
        else throw new Exception("receiver's username isn't valid");

        PrivateChatModel privateChat = privateChatService.getChatById(privateChatId);
        if (privateChat == null) throw new Exception("private-chat not found");

        MessageModel messageModel = new MessageModel();
        messageModel.setContent(content);
        messageModel.setSenderUsername(therapist.getUsername());
        messageModel.setSenderId(therapist.getId());
        messageModel.setReceiverUsername(receiver.getUsername());
        messageModel.setReceiverId(receiver.getId());
        messageModel.setChatType(ChatType.PRIVATE);
        messageModel.setIsRead(false);
        messageModel.setTimestamp(LocalDateTime.now());
        messageModel.setPrivateChatId(privateChat.getId());
        messageModel.setStatus(MessageStatus.SENT);

        return sendMessage(messageModel);
    }

    public boolean sendPublicMessage(String content) throws Exception {

        if (content.isEmpty())
            throw new Exception("can't send empty message");

        SysUserEntity currentUser = identityProvider.currentIdentity();

        MessageModel messageModel = new MessageModel();
        messageModel.setContent(content);
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

    public List<MessageModel> getAllPublicMessages() {
        return messageRepository.findAllByChatType(ChatType.PUBLIC);
    }

    public boolean deleteMessage(Long messageId) {
        SysUserEntity currentUser = identityProvider.currentIdentity();
        isValidDelete(currentUser, messageId);
        return messageRepository.deleteMessage(messageId, currentUser.getId());
    }

    private void isValidDelete(SysUserEntity currentUser, Long messageId) {
        MessageModel message = messageRepository.findById(messageId);

        if (currentUser.getId().equals(message.getSenderId())) return;

        if ((currentUser.getRole() == UserRole.ADMIN && message.getSenderRole() == UserRole.ADMIN)
                || (currentUser.getRole() == UserRole.MODERATOR && message.getSenderRole() == UserRole.ADMIN)
                || (currentUser.getRole() == UserRole.MODERATOR && message.getSenderRole() == UserRole.MODERATOR)) {
            throw new RuntimeException("invalid delete message");
        }

    }

    public boolean editMessage(Long messageId, String content) throws Exception {
        boolean isOwner = (Objects.equals(identityProvider.currentIdentity().getId(), messageRepository.findSenderId(messageId)));

        if (isOwner)
            return messageRepository.updateMessageContent(messageId, content);
        else
            throw new Exception("can't edit, message doesn't belong to the user");
    }

    private MessageModel sendMessage(MessageModel messageModel) {
        if (messageModel.getChatType() != ChatType.PUBLIC) {
            NotificationModel notification = NotificationModel.builder()
                    .isRead(false)
                    .receiverId(messageModel.getReceiverId())
                    .content("you have message from " + identityProvider.currentIdentity().getUsername() + " check your chat")
                    .createdOn(LocalDateTime.now())
                    .type("message")
                    .build();
            notificationService.createNotification(notification);
        }

        return messageRepository.sendMessage(messageModel);
    }

    public Boolean attachFile( FileModel fileModel,String fileAsBase64) throws IOException {

        if (fileModel==null&& fileAsBase64.isEmpty())
            throw new RuntimeException("can't send empty file");

        SysUserEntity currentUser = identityProvider.currentIdentity();
        SysUserEntity therapist = sysUserRepository.findByUsername("omar_khaled").get();
        PrivateChatModel privateChat = privateChatRepository.findByUserId(currentUser.getId());

        if (privateChat == null) {
            privateChat = privateChatService.createChatBetween(currentUser.getId(), therapist.getId());
        }


        fileModel.setPrivateChatId(privateChat.getId());
        fileModel.setChatType(ChatType.PRIVATE);
        FileModel file= fileService.attachPrivateChatFile(fileModel,fileAsBase64);


        MessageModel messageModel = new MessageModel();
        messageModel.setContent(fileModel.getName());
        messageModel.setSenderUsername(currentUser.getUsername());
        messageModel.setSenderId(currentUser.getId());
        messageModel.setReceiverUsername(therapist.getUsername());
        messageModel.setReceiverId(therapist.getId());
        messageModel.setChatType(ChatType.PRIVATE);
        messageModel.setIsRead(false);
        messageModel.setTimestamp(LocalDateTime.now());
        messageModel.setPrivateChatId(privateChat.getId());
        messageModel.setStatus(MessageStatus.SENT);
        messageModel.setIsFile(true);
        messageModel.setFileId(file.getId());

        sendMessage(messageModel);
        return true;
    }

    public boolean attachFileFromTherapist(Long chatId, FileModel fileModel, String fileAsBase64) throws IOException {
        if (fileModel==null&& fileAsBase64.isEmpty())
            throw new RuntimeException("can't send empty file");

        SysUserEntity currentUser = identityProvider.currentIdentity();
        PrivateChatModel privateChat = privateChatRepository.findById(chatId);


        fileModel.setPrivateChatId(chatId);
        fileModel.setChatType(ChatType.PRIVATE);
        FileModel file= fileService.attachPrivateChatFile(fileModel,fileAsBase64);


        MessageModel messageModel = new MessageModel();
        messageModel.setContent(fileModel.getName());
        messageModel.setSenderUsername(currentUser.getUsername());
        messageModel.setSenderId(currentUser.getId());
        messageModel.setReceiverUsername(privateChat.getUserUsername());
        messageModel.setReceiverId(privateChat.getUserId());
        messageModel.setChatType(ChatType.PRIVATE);
        messageModel.setIsRead(false);
        messageModel.setTimestamp(LocalDateTime.now());
        messageModel.setPrivateChatId(chatId);
        messageModel.setStatus(MessageStatus.SENT);
        messageModel.setIsFile(true);
        messageModel.setFileId(file.getId());

        sendMessage(messageModel);
        return true;
    }
}
