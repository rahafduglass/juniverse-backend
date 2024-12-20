package org.example.chatbackend.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatbackend.domain.enums.ChatType;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="public_chat")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublicChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy="PublicChatEntity") //*REQUIRED* has substitutions.
    private Set<UserEntity> users;


}
