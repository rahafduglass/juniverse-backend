package org.example.chatbackend.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.chatbackend.domain.enums.MessageStatus;

import java.time.LocalDateTime;

@Entity
@Table(name="message")
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="time_stamp")
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name="sender_id", referencedColumnName = "id")
    private UserEntity senderId;

    @ManyToOne
    @JoinColumn(name="public_chat_id",referencedColumnName = "id")
    private PublicChatEntity PublicChatId; //

    @ManyToOne
    @JoinColumn(name="private_chat_id",referencedColumnName = "id")
    private PrivateChatEntity privateChatId;

    @Column(name="status")
    private MessageStatus status;
}
