package org.example.chatbackend.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatbackend.domain.enums.ChatType;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="private_chat")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrivateChatEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity receiverId;//therapist

    @OneToOne
    private UserEntity senderId;

    @OneToMany(mappedBy = "MessagesEntity")
    private List<MessageEntity> messages;



}
