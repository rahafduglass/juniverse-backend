package juniverse.chatbackend.domain.mappers;

import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SysUserMapper {

    SysUserModel entityToModel(SysUserEntity sysUserEntity);

    SysUserEntity modelToEntity(SysUserModel sysUserModel);
}
