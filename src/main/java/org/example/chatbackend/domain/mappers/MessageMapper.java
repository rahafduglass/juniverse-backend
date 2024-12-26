package org.example.chatbackend.domain.mappers;

import org.example.chatbackend.application.dtos.private_message.MessageRequest;
import org.example.chatbackend.application.dtos.private_message.MessageResponse;
import org.example.chatbackend.domain.models.MessageModel;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageModel requestToModel(MessageRequest messageRequest);

    MessageResponse modelToResponse(MessageModel messageModel);

    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "receiverId", target = "receiver.id")
    @Mapping(source = "privateChatId", target = "privateChat.id")
    MessageEntity modelToEntity(MessageModel messageModel);

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "privateChat.id", target = "privateChatId")
    MessageModel entityToModel(MessageEntity messageEntity);

    List<MessageResponse> listOfModelsToListOfResponses(List<MessageModel> listOfMessages);

    List<MessageModel> listOfEntitiesToListOfModels(List<MessageEntity> listOfMessages);
}
