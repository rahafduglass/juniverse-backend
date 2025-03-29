package juniverse.domain.services;

import juniverse.domain.models.EventModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final IdentityProvider identityProvider;

    public boolean addEvent(EventModel eventModel) {
        eventModel.setCreatedById(identityProvider.currentIdentity().getId());
        return eventRepository.addEvent(eventModel);
    }
}
