package juniverse.chatbackend.domain.mappers;

import juniverse.chatbackend.application.dtos.authentication.LoginRequest;
import juniverse.chatbackend.application.dtos.authentication.LoginResponse;
import juniverse.chatbackend.domain.models.UserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel requestToModel(LoginRequest loginRequest);


    LoginResponse modelToResponse(UserModel userModel);

    UserModel entityToModel(SysUserEntity sysUserEntity);

    SysUserEntity modelToEntity(UserModel userModel);
}
