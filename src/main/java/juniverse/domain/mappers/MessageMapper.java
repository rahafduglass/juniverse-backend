package juniverse.domain.mappers;

import juniverse.application.dtos.chats.private_chat.MessageResponse;
import juniverse.domain.models.MessageModel;
import juniverse.persistance.entities.MessageEntity;
import juniverse.persistance.entities.PrivateChatEntity;
import juniverse.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageResponse modelToResponse(MessageModel messageModel);

    List<MessageModel> listOfEntitiesToListOfModels(List<MessageEntity> listOfMessages);

    List<MessageResponse> listOfModelsToListOfTherapistResponses(List<MessageModel> listOfMessages);

    List<juniverse.application.dtos.chats.public_chat.MessageResponse> listOfModelsToListOfResponses(List<MessageModel> listOfMessages);

    @Mapping(source = "receiverId", target = "receiver")
    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "privateChatId", target = "privateChat")
    MessageEntity modelToEntity(MessageModel messageModel);

    @Mapping(source = "sender.username", target = "senderUsername")
    @Mapping(source = "receiver.username", target = "receiverUsername")
    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "privateChat.id", target = "privateChatId")
    MessageModel entityToModel(MessageEntity messageEntity);


    default PrivateChatEntity mapPrivateChatIdToEntity(Long privateChatId) {
        if (privateChatId == null) {
            return null;  // Return null if there's no privateChatId
        }

        PrivateChatEntity privateChatEntity = new PrivateChatEntity();
        privateChatEntity.setId(privateChatId);

        return privateChatEntity;
    }

    default SysUserEntity mapSysUserIdToEntity(Long sysUserId) {
        if (sysUserId == null) {
            return null;  // Return null if there's no privateChatId
        }

        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(sysUserId);

        return sysUserEntity;
    }

}
