package org.example.chatbackend.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatbackend.domain.enums.ChatType;

import java.time.LocalDateTime;

@Entity(name="message")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = true) // nullable for public chat
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private ChatType chatType; // Enum for chat type: PUBLIC or PRIVATE

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private SysUserEntity senderId; //always needed either a user to public OR user to therapist OR therapist to user

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = true)
    private SysUserEntity receiverId; //nullable for public chat

}
