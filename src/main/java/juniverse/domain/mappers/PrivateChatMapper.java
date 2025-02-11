package juniverse.domain.mappers;

import juniverse.application.dtos.private_chat.TherapistChatResponse;
import juniverse.application.dtos.private_chat.UserChatResponse;
import juniverse.domain.models.PrivateChatModel;
import juniverse.persistance.entities.PrivateChatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

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

    UserChatResponse modelToUserChatResponse(PrivateChatModel chat);

    List<TherapistChatResponse> listOfModelsToListOfResponses(List<PrivateChatModel> privateChatModels);
}