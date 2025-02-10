package juniverse.chatbackend.persistance.adapter;

import juniverse.chatbackend.domain.mappers.SysUserMapper;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.jpa.SysUserJpaRepository;
import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
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
    public Boolean updateBio(SysUserEntity sysUserEntity) {
        return sysUserJpaRepository.updateBio(sysUserEntity.getBio(), sysUserEntity.getId()) > 0;
    }

    @Override
    public Boolean updateProfilePicturePath(Long userId, String path) {
        return sysUserJpaRepository.updateProfilePicturePath(userId, path) > 0;
    }

    @Override
    public String findProfilePicturePath(Long id) {
        return sysUserJpaRepository.findProfilePicturePath(id);
    }

    @Override
    public String findCoverPicturePath(Long id) {
        return sysUserJpaRepository.findCoverPicturePath(id);
    }

    @Override
    public Boolean updateCoverPicturePath(Long id, String filePath) {
        return sysUserJpaRepository.updateCoverPicturePath(id, filePath) > 0;
    }
}
