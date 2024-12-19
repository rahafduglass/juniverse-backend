package org.example.chatbackend.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatbackend.domain.enums.ChatType;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="chat")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="chat_type")
    private ChatType chatType; //0 PUBLIC
    // 1 GENERAL

    @ManyToOne
    @JoinColumn(name = "therapist_id", nullable = false)
    private UserEntity therapist_id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MessageEntity> messages; //replaces querying *OPTIONAL*

    @ManyToMany(fetch = FetchType.LAZY) //*REQUIRED* has substitutions.
    @JoinTable(
            name="chat_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> users;


}
