package juniverse.persistance.entities.chat;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import juniverse.persistance.entities.user.SysUserEntity;
import lombok.Data;

@Entity(name = "note")
@Data
@Table
public class TherapistNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @ManyToOne
    @NotNull
    private PrivateChatEntity privateChat;

    @ManyToOne
    @NotNull
    private SysUserEntity therapist;
}
