package juniverse.persistance.entities;

import jakarta.persistence.*;
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
    private int id;

    private String title;

    private String content;

    @ManyToOne
    private SysUserEntity author;

    @ManyToOne
    private SysUserEntity updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
