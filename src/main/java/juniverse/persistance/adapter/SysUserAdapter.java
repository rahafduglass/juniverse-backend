package juniverse.persistance.adapter;

import juniverse.domain.mappers.SysUserMapper;
import juniverse.domain.models.SysUserModel;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.jpa.SysUserJpaRepository;
import juniverse.persistance.repositories.SysUserRepository;
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
    public Boolean updateProfilePicturePath(Long userId, String path,String fileExtension) {
        return sysUserJpaRepository.updateProfilePicturePath(userId, path,fileExtension) > 0;
    }

    @Override
    public Object[]  findProfilePicturePath(Long id) {
        return sysUserJpaRepository.findProfilePicturePath(id);
    }

    @Override
    public Object[] findCoverPicturePath(Long id) {
        return sysUserJpaRepository.findCoverPicturePath(id);
    }

    @Override
    public Boolean updateCoverPicturePath(Long id, String filePath,String fileExtension) {
        return sysUserJpaRepository.updateCoverPicturePath(id, filePath,fileExtension) > 0;
    }

    @Override
    public boolean deleteProfilePicture(Long currentUserId) {
        return sysUserJpaRepository.deleteProfilePicture(currentUserId)>0;
    }
}
