package juniverse.persistance.entities;

import jakarta.persistence.*;
import juniverse.domain.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import juniverse.domain.enums.ChatType;

import java.time.LocalDateTime;

@Entity(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = true) // nullable for public chat
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatType chatType; // Enum for chat type: PUBLIC or PRIVATE

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus status;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private SysUserEntity sender; //always needed either a user to public OR user to therapist OR therapist to user

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = true)
    private SysUserEntity receiver; //nullable for public chat

    @ManyToOne
    @JoinColumn(name = "private_chat_id", nullable = true)//null if its public chat
    private PrivateChatEntity privateChat;

    @ManyToOne
    @JoinColumn(name = "public_chat_id", nullable = true)
    private PublicChatEntity publicChat;


    @ManyToOne
    @JoinColumn(name="deleted_by")
    private SysUserEntity deletedBy;


}
