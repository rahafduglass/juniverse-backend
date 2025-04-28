package juniverse.persistance.adapter.user;

import juniverse.domain.enums.UserRole;
import juniverse.domain.mappers.user.SysUserMapper;
import juniverse.domain.models.user.SysUserModel;
import juniverse.persistance.entities.user.SysUserEntity;
import juniverse.persistance.jpa.user.SysUserJpaRepository;
import juniverse.persistance.repositories.user.SysUserRepository;
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

    @Override
    public boolean deleteCoverPicture(Long currentUserId) {
        return sysUserJpaRepository.deleteCoverPicture(currentUserId)>0;
    }

    @Override
    public boolean updateRole(Long studentId, UserRole userRole) {
        return sysUserJpaRepository.updateRole(studentId,userRole)>0;
    }

    @Override
    public UserRole findRoleById(Long studentId) {
        return sysUserJpaRepository.findRoleById(studentId);
    }

    @Override
    public boolean banUser(Long userId) {
        return sysUserJpaRepository.banUser(userId)>0;
    }

    @Override
    public List<SysUserModel> findUsersByRole(UserRole role) {
        return (sysUserJpaRepository.findUsersByRole(role)).stream().map(sysUserMapper::entityToModel).toList();
    }

    @Override
    public List<SysUserModel> findBannedUsers() {
        return sysUserJpaRepository.findBannedUsers().stream().map(sysUserMapper::entityToModel).toList();
    }

    @Override
    public boolean unbanUser(Long userId) {
        return sysUserJpaRepository.unbanUser(userId)>0;
    }
}
