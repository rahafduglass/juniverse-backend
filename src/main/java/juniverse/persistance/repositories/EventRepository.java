package juniverse.persistance.repositories;

import juniverse.domain.models.EventModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository {
    boolean addEvent(EventModel eventModel);

    List<EventModel> getEvents();

    boolean deleteEvent(Long eventId);
}
