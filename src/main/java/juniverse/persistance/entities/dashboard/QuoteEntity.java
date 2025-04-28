package juniverse.persistance.entities.dashboard;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import juniverse.persistance.entities.user.SysUserEntity;
import lombok.Data;

@Entity(name="quote")
@Data
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String quote;

    @OneToOne
    @NotNull
    private SysUserEntity owner;

}
