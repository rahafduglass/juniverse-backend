package juniverse.domain.services.news;

import juniverse.domain.models.news.EventModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.news.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final IdentityProvider identityProvider;

    public boolean addEvent(EventModel eventModel) {
        eventModel.setCreatedById(identityProvider.currentIdentity().getId());
        return eventRepository.addEvent(eventModel);
    }

    public List<EventModel> getEvents() {
        return eventRepository.getEvents();
    }

    public boolean deleteEvent(Long eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}
