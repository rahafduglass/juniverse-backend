package org.example.chatbackend.domain.mappers;

import org.example.chatbackend.application.dtos.authentication.LoginRequest;
import org.example.chatbackend.application.dtos.authentication.LoginResponse;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel requestToModel(LoginRequest loginRequest);


    LoginResponse modelToResponse(UserModel userModel);

    UserModel entityToModel(SysUserEntity sysUserEntity);

    SysUserEntity modelToEntity(UserModel userModel);
}
