package org.example.chatbackend.domain.mappers;
import org.example.chatbackend.application.dtos.SendMessageRequest;
import org.example.chatbackend.application.dtos.MessageResponse;
import org.example.chatbackend.domain.models.Message;
import org.example.chatbackend.persistance.entities.MessageEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
     Message requestToModel(SendMessageRequest sendMessageRequest) ;
    MessageResponse modelToResponse(Message message);

    MessageEntity modelToEntity(Message message);

    Message entityToModel(MessageEntity messageEntity);

    List<MessageResponse> modelListToResponseList(List<Message> messageList);
}
