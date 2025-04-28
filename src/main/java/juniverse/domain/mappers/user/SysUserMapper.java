package juniverse.domain.mappers.user;

import juniverse.application.dtos.sys_user.RegisterRequest;
import juniverse.application.dtos.sys_user.SysUserProfileResponse;
import juniverse.application.dtos.sys_user.SysUserResponse;
import juniverse.application.dtos.sys_user.UpdateBioRequest;
import juniverse.domain.models.user.SysUserModel;
import juniverse.persistance.entities.user.SysUserEntity;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SysUserMapper {

    SysUserModel entityToModel(SysUserEntity sysUserEntity);

    SysUserEntity modelToEntity(SysUserModel sysUserModel);

    List<SysUserModel> listOfRequestsToListOfModel(List<RegisterRequest> registerRequests);

    SysUserProfileResponse modelToDTO(SysUserModel profile);

    SysUserModel requestToModel(UpdateBioRequest request);


    SysUserResponse modelToResponse(SysUserModel sysUserModel);
}
