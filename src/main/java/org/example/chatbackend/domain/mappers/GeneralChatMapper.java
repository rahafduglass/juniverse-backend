package org.example.chatbackend.domain.mappers;
import org.example.chatbackend.application.dtos.SendMessageRequest;
import org.example.chatbackend.application.dtos.SendMessageResponse;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneralChatMapper {
     Message requestToModel(SendMessageRequest sendMessageRequest) ;
    SendMessageResponse modelToResponse(Message message);

    MessageEntity modelToEntity(Message message);

    Message entityToModel(MessageEntity messageEntity);
}
