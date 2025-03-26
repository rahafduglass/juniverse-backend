package juniverse.domain.models;

import juniverse.persistance.entities.SysUserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsModel {
    private Long id;

    private String title;

    private String content;

    private Long authorId;

    private String authorUserName;



    private Long updatedById;



    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
