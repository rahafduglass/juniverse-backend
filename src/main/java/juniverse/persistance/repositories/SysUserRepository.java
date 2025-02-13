package juniverse.persistance.repositories;

import juniverse.domain.models.SysUserModel;
import juniverse.persistance.entities.SysUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysUserRepository {
    SysUserModel findById(Long id);

    Optional<SysUserEntity> findByUsername(String username);

    List<SysUserEntity> saveAll(List<SysUserEntity> users);

    Boolean updateBio(SysUserEntity sysUserEntity);

    Boolean updateProfilePicturePath(Long userId,String path,String fileExtension);

    Object[] findProfilePicturePath(Long id);

    Object[] findCoverPicturePath(Long id);

    Boolean updateCoverPicturePath(Long id, String filePath,String fileExtension);
}
