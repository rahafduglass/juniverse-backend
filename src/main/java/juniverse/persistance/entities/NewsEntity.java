package juniverse.persistance.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "news")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @ManyToOne
    @NotNull
    private SysUserEntity author;

    @ManyToOne
    private SysUserEntity updatedBy;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
