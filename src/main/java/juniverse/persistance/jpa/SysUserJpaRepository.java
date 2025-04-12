package juniverse.persistance.jpa;

import io.micrometer.common.KeyValues;
import jakarta.transaction.Transactional;
import juniverse.domain.enums.UserRole;
import juniverse.persistance.entities.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Transactional
    @Modifying
    @Query("UPDATE sys_user s SET s.role =:userRole WHERE s.id = :id")
    Integer updateRole(Long id, UserRole userRole);


    @Query(" SELECT u.role FROM sys_user u WHERE u.id = :id")
    UserRole findRoleById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE sys_user s SET s.isBanned =true WHERE s.id = :id")
    Integer banUser(Long id);

    @Query(" SELECT u FROM sys_user u WHERE u.role = :role")
    List<SysUserEntity> findUsersByRole(UserRole role);
}
