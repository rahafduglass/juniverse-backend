package juniverse.persistance.jpa;

import juniverse.persistance.entities.PrivateChatEntity;
import juniverse.persistance.entities.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateChatJpaRepository extends JpaRepository<PrivateChatEntity, Long> {


    @Query("SELECT pc, su FROM private_chat pc JOIN sys_user su ON pc.user.id = su.id WHERE pc.therapist.id = :therapistId")
    List<Object[]> findChatsWithUserDetailsByTherapist(@Param("therapistId") Long therapistId);

    @Query("SELECT pc FROM private_chat pc WHERE pc.user.id = :userId")
    PrivateChatEntity findPrivateChatEntityByUser(Long userId);


    PrivateChatEntity findByUser(SysUserEntity sysUserEntity);

    PrivateChatEntity findByUserId(Long userId);
}
