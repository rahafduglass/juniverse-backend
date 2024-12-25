package org.example.chatbackend.domain.mappers;

import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrivateChatMapper {
    @Mapping(source = "therapist.id", target = "therapistId")
    @Mapping(source = "user.id", target = "userId")
    PrivateChatModel entityToModel(PrivateChatEntity privateChatEntity);

    @Mapping(source = "therapistId", target = "therapist.id")
    @Mapping(source = "userId", target = "user.id")
    PrivateChatEntity modelToEntity(PrivateChatModel privateChatModel);
}