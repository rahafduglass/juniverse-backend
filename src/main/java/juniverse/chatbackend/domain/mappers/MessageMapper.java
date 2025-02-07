package juniverse.chatbackend.domain.mappers;

import juniverse.chatbackend.application.dtos.private_chat.messages.MessageRequest;
import juniverse.chatbackend.application.dtos.private_chat.messages.MessageResponse;
import juniverse.chatbackend.domain.models.MessageModel;
import juniverse.chatbackend.persistance.entities.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {


    MessageModel requestToModel(MessageRequest messageRequest);

    MessageResponse modelToResponse(MessageModel messageModel);


    @Mapping(source="senderUsername", target= "sender.username")
    @Mapping(source="receiverUsername", target="receiver.username")
    @Mapping(source="receiverId", target="receiver.id")
    @Mapping(source="senderId", target="sender.id")
    @Mapping(source = "privateChatId", target = "privateChat.id")
    MessageEntity modelToEntity(MessageModel messageModel);

    @Mapping(source = "sender.username", target = "senderUsername")
    @Mapping(source = "receiver.username", target = "receiverUsername")
    @Mapping(source="receiver.id", target="receiverId")
    @Mapping(source="sender.id", target="senderId")
    @Mapping(source = "privateChat.id", target = "privateChatId")
    MessageModel entityToModel(MessageEntity messageEntity);

    List<MessageModel> listOfEntitiesToListOfModels(List<MessageEntity> listOfMessages);

    List<MessageResponse> listOfModelsToListOfResponses(List<MessageModel> listOfMessages);
}
