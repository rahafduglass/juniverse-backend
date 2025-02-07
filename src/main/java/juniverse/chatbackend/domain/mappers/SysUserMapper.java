package juniverse.chatbackend.domain.mappers;

import juniverse.chatbackend.application.dtos.RegisterRequest;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SysUserMapper {

    SysUserModel entityToModel(SysUserEntity sysUserEntity);

    SysUserEntity modelToEntity(SysUserModel sysUserModel);

    List<SysUserModel> listOfRequestsToListOfModel(List<RegisterRequest> registerRequests);
}
