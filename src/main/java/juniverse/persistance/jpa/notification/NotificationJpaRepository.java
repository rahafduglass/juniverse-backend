package juniverse.persistance.jpa.notification;

import juniverse.persistance.entities.notification.NotificationEntity;
import org.apache.logging.log4j.simple.internal.SimpleProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("SELECT n FROM notification n WHERE n.receiver.id=:receiverId")
    List<NotificationEntity> findAllByReceiverId(Long receiverId);
}
