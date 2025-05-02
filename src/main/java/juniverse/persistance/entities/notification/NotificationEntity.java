package juniverse.persistance.entities.notification;

import jakarta.persistence.*;
import juniverse.persistance.entities.user.SysUserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "notification")
@Data
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Boolean isRead;

    private String type;

    private LocalDateTime createdOn;

    @ManyToOne
    private SysUserEntity receiver;
}
