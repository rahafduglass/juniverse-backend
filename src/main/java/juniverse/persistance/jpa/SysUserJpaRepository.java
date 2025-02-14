package juniverse.persistance.jpa;

import jakarta.transaction.Transactional;
import juniverse.persistance.entities.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserJpaRepository extends JpaRepository<SysUserEntity, Long> {


    Optional<SysUserEntity> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE sys_user u SET u.bio = :bio WHERE u.id = :id")
    Integer updateBio(@Param("bio")String bio, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE sys_user u SET u.profilePicturePath = :path,u.profilePictureExtension=:fileExtension WHERE u.id = :id")
    Integer updateProfilePicturePath(Long id, String path,String fileExtension);

    @Query("SELECT u.profilePicturePath,u.profilePictureExtension FROM sys_user u WHERE u.id = :id")
    Object[] findProfilePicturePath(Long id);

    @Query("SELECT u.coverPicturePath ,u.coverPictureExtension FROM sys_user u WHERE u.id = :id")
    Object[] findCoverPicturePath(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE sys_user u SET u.coverPicturePath = :path , u.coverPictureExtension=:coverPictureExtension WHERE u.id = :id")
    Integer updateCoverPicturePath(Long id, String path,String coverPictureExtension);

    @Transactional
    @Modifying
    @Query("UPDATE sys_user s SET s.profilePicturePath = NULL,s.profilePictureExtension= NULL WHERE s.id = :id")
    Integer deleteProfilePicture(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE sys_user s SET s.coverPicturePath = NULL,s.coverPictureExtension= NULL WHERE s.id = :id")
    Integer deleteCoverPicture(Long id);
}
