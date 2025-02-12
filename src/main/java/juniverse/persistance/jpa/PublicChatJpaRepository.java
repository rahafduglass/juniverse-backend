package juniverse.persistance.jpa;


import juniverse.persistance.entities.PublicChatEntity;
import juniverse.persistance.entities.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicChatJpaRepository extends JpaRepository<PublicChatEntity, Long> {

}
