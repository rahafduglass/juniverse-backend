package juniverse.persistance.repositories;

import juniverse.domain.models.EventModel;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository {
    boolean addEvent(EventModel eventModel);
}
