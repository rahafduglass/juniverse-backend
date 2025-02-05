package juniverse.chatbackend.domain.mappers;

import juniverse.chatbackend.application.dtos.authentication.LoginRequest;
import juniverse.chatbackend.application.dtos.authentication.LoginResponse;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SysUserMapper {
    SysUserModel requestToModel(LoginRequest loginRequest);


    LoginResponse modelToResponse(SysUserModel sysUserModel);

    SysUserModel entityToModel(SysUserEntity sysUserEntity);

    SysUserEntity modelToEntity(SysUserModel sysUserModel);
}
