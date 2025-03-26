package juniverse.application.dtos.news;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsRequest {

    private String title;

    private String content;

}
