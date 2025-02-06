package juniverse.chatbackend.domain.mappers;

import juniverse.chatbackend.application.dtos.private_chat.TherapistChatResponse;
import juniverse.chatbackend.application.dtos.private_chat.UserChatResponse;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.persistance.entities.PrivateChatEntity;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrivateChatMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "userUsername")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "therapist.id", target = "therapistId")
    @Mapping(source = "therapist.username", target = "therapistUsername")
    @Mapping(source = "therapist.firstName", target = "therapistFirstName")
    @Mapping(source = "therapist.lastName", target = "therapistLastName")
    PrivateChatModel entityToModel(PrivateChatEntity chat);

    @Mapping(source = "therapistId", target = "therapist.id")
    @Mapping(source = "userId", target = "user.id")
    PrivateChatEntity modelToEntity(PrivateChatModel privateChatModel);


    @Mapping(source = "chat.id", target = "id")
    @Mapping(source = "chat.user.username", target = "userUsername")
    @Mapping(source = "chat.user.firstName", target = "userFirstName")
    @Mapping(source = "chat.user.lastName", target = "userLastName")
    TherapistChatResponse entityToTherapistChatResponse(PrivateChatEntity chat);



    UserChatResponse modelToUserChatResponse(PrivateChatModel chat);
}