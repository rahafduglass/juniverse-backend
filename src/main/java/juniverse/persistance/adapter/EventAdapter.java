package juniverse.persistance.adapter;

import jdk.jfr.Event;
import juniverse.domain.mappers.EventMapper;
import juniverse.domain.models.EventModel;
import juniverse.persistance.jpa.EventJpaRepository;
import juniverse.persistance.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventAdapter implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final EventMapper eventMapper;

    @Override
    public boolean addEvent(EventModel eventModel) {
        eventJpaRepository.save(eventMapper.modelToEntity(eventModel));
        return true;
    }
}
