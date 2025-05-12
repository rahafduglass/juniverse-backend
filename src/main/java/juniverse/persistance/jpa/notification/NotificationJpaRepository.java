package juniverse.persistance.jpa.notification;

import jakarta.transaction.Transactional;
import juniverse.persistance.entities.notification.NotificationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("SELECT n FROM notification n WHERE n.receiver.id = :receiverId AND n.isRead=true ORDER BY n.createdOn DESC")
    List<NotificationEntity> findAllReadNotificationsByReceiverId(Long receiverId, Pageable pageable);


    @Query("SELECT n FROM notification n WHERE n.receiver.id = :receiverId AND n.isRead=false ORDER BY n.createdOn DESC")
    List<NotificationEntity> findAllUnReadNotificationsByReceiverId(Long receiverId);


    @Transactional
    @Modifying
    @Query("UPDATE notification n SET n.isRead=true WHERE n.id IN :ids")
    int updateAsRead(List<Long> ids);
}
