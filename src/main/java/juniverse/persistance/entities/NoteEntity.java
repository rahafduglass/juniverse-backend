package juniverse.persistance.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "note")
@Data
@Table
public class NoteEntity {

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
