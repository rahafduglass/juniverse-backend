package juniverse.persistance.repositories.user;

import juniverse.domain.enums.UserRole;
import juniverse.domain.models.user.SysUserModel;
import juniverse.persistance.entities.user.SysUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysUserRepository {

    Optional<SysUserEntity> findByUsername(String username);

    List<SysUserEntity> saveAll(List<SysUserEntity> users);

    Boolean updateBio(SysUserEntity sysUserEntity);

    Boolean updateProfilePicturePath(Long userId,String path,String fileExtension);

    Object[] findProfilePicturePath(Long id);

    Object[] findCoverPicturePath(Long id);

    Boolean updateCoverPicturePath(Long id, String filePath,String fileExtension);

    boolean deleteProfilePicture(Long currentUserId);

    boolean deleteCoverPicture(Long currentUserId);

    boolean updateRole(Long studentId, UserRole userRole);

    UserRole findRoleById(Long studentId);

    boolean banUser(Long userId);

    List<SysUserModel> findUsersByRole(UserRole role);

    List<SysUserModel> findBannedUsers();

    boolean unbanUser(Long userId);

    List<SysUserModel> findAll();
}
