package juniverse.persistance.entities.news;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import juniverse.persistance.entities.user.SysUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity(name = "event")
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String location;

    @NotNull
    private String date;

    @NotNull
    private Timestamp time;

    @NotNull
    @ManyToOne
    private SysUserEntity createdBy;
}
