package juniverse.chatbackend.domain.services;

import juniverse.chatbackend.domain.mappers.SysUserMapper;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.domain.provider.IdentityProvider;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

@Service
@RequiredArgsConstructor
public class SysUserService {

    private final IdentityProvider identityProvider;
    private final SysUserRepository sysUserRepository;
    private final SysUserMapper sysUserMapper;

    public SysUserModel getProfile() {
        return sysUserMapper.entityToModel(sysUserRepository.findByUsername(identityProvider.currentIdentity().getUsername()).get());
    }


    public Boolean updateProfile(SysUserModel sysUserModel) {
        sysUserModel.setId(identityProvider.currentIdentity().getId());
        return sysUserRepository.updateProfile(sysUserMapper.modelToEntity(sysUserModel));
    }
}
