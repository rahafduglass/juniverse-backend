package juniverse.persistance.adapter.news;

import juniverse.domain.mappers.news.EventMapper;
import juniverse.domain.models.news.EventModel;
import juniverse.persistance.jpa.news.EventJpaRepository;
import juniverse.persistance.repositories.news.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<EventModel> getEvents() {
        return eventMapper.entityToModel(eventJpaRepository.findAll());
    }

    @Override
    public boolean deleteEvent(Long eventId) {
        eventJpaRepository.deleteById(eventId);
        return true;
    }
}
