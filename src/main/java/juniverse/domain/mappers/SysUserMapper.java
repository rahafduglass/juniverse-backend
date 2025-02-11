package juniverse.domain.mappers;

import juniverse.application.dtos.sys_user.RegisterRequest;
import juniverse.application.dtos.sys_user.SysUserProfileResponse;
import juniverse.application.dtos.sys_user.UpdateBioRequest;
import juniverse.domain.models.SysUserModel;
import juniverse.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SysUserMapper {

    SysUserModel entityToModel(SysUserEntity sysUserEntity);

    SysUserEntity modelToEntity(SysUserModel sysUserModel);

    List<SysUserModel> listOfRequestsToListOfModel(List<RegisterRequest> registerRequests);

    SysUserProfileResponse modelToDTO(SysUserModel profile);

    SysUserModel requestToModel(UpdateBioRequest request);


}
