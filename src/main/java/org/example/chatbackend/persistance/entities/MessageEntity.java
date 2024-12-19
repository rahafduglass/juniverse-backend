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
    private int id;

    @Column(name="content")
    private String content;

    @Column(name="time_stamp")
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name="sender_id", referencedColumnName = "id")
    private UserEntity senderId;

    @ManyToOne
    @JoinColumn(name="chat_id",referencedColumnName = "id")
    private ChatEntity chatId; // to get type "private" or "public"

    @Column(name="status")
    private MessageStatus status;
}
