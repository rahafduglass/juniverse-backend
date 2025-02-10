package juniverse.chatbackend.persistance.adapter;

import juniverse.chatbackend.domain.provider.IdentityProvider;
import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.mappers.SysUserMapper;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.jpa.SysUserJpaRepository;
import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SysUserAdapter implements SysUserRepository {

    private final SysUserJpaRepository sysUserJpaRepository;
    private final SysUserMapper sysUserMapper;

    @Override
    public SysUserModel findById(Long id) {
        Optional<SysUserEntity> userEntity = sysUserJpaRepository.findById(id);
        if (userEntity.isEmpty()) {
            return null;
        }
        return sysUserMapper.entityToModel(userEntity.get());
    }

    @Override
    public Optional<SysUserEntity> findByUsername(String username) {
        return sysUserJpaRepository.findByUsername(username);
    }

    @Override
    public List<SysUserEntity> saveAll(List<SysUserEntity> users) {
        return sysUserJpaRepository.saveAll(users);
    }

    @Override
    public SysUserEntity update(SysUserEntity sysUserEntity) {
        return sysUserJpaRepository.save(sysUserEntity);
    }

    @Override
    public Boolean updateProfile(SysUserEntity sysUserEntity) {
        return sysUserJpaRepository.updateProfileById(sysUserEntity.getBio(), sysUserEntity.getId()) > 0;
    }

    @Override
    public Boolean updateProfilePicturePath(Long userId, String path) {
        return sysUserJpaRepository.updateProfilePicturePath(userId, path) > 0;
    }

    @Override
    public String findProfilePicturePath(Long id) {
        return sysUserJpaRepository.findProfilePicturePath(id);
    }
}
