package juniverse.domain.models.news;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EventModel {
    private Long id;

    private String title;

    private String description;

    private String location;

    private String date;

    private Timestamp time;

    private String createdByUsername;

    private Long createdById;
}
