package juniverse.chatbackend.persistance.jpa;

import juniverse.chatbackend.persistance.entities.PrivateChatEntity;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateChatJpaRepository extends JpaRepository<PrivateChatEntity, Long> {


    PrivateChatEntity findPrivateChatEntityByUser(SysUserEntity senderId);

    List<PrivateChatEntity> findAllByTherapistId(Long therapistId);
}
