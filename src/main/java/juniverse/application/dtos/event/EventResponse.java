package juniverse.application.dtos.event;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EventResponse {

    private Long id;

    private String title;

    private String description;

    private String location;

    private String date;

    private Timestamp time;

    private String createdByUsername;

    private Long createdById;
}
