package juniverse.chatbackend.domain.mappers;

import juniverse.chatbackend.application.dtos.private_chat.PrivateChatResponse;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.persistance.entities.PrivateChatEntity;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PrivateChatMapper {
    @Mapping(source = "therapist.id", target = "therapistId")
    @Mapping(source = "user.id", target = "userId")
    PrivateChatModel entityToModel(PrivateChatEntity privateChatEntity);

    @Mapping(source = "therapistId", target = "therapist.id")
    @Mapping(source = "userId", target = "user.id")
    PrivateChatEntity modelToEntity(PrivateChatModel privateChatModel);


    @Mapping(source = "chat.id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "chat.therapist.id", target = "therapistId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    PrivateChatResponse entityToResponse(PrivateChatEntity chat, SysUserEntity user);


}