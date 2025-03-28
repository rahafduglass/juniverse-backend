package juniverse.application.dtos.news;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsResponse {
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
