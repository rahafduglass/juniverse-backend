package juniverse.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="private_chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="private_chat")
public class PrivateChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER) //each user has one private chat
    @JoinColumn(name = "user_id", nullable = false)
    private SysUserEntity user;

    @ManyToOne(fetch = FetchType.EAGER) //one therapist has many private chats
    @JoinColumn(name = "therapist_id", nullable = false)
    private SysUserEntity therapist; //it'll be set from model as lookup data

}
