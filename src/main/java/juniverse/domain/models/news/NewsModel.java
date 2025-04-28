package juniverse.domain.models.news;

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

    private String updatedByUserName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
