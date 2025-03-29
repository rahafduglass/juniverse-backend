package juniverse.application.dtos.event;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EventRequest {

    private String title;

    private String description;

    private String location;

    private String date;

    private Timestamp time;
}
