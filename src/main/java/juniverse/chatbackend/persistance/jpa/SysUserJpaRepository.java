package juniverse.chatbackend.persistance.jpa;

import jakarta.transaction.Transactional;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
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
    Integer updateProfileById(@Param("bio")String bio,@Param("id") Long id);


}
