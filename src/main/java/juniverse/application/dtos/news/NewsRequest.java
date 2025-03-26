package juniverse.application.dtos.news;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsRequest {


    private Long id;

    private String title;

    private String content;

    private Long authorId;

    private Long updatedById;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
