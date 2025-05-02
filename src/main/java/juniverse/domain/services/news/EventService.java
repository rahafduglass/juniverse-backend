package juniverse.domain.services.news;

import juniverse.domain.models.news.EventModel;
import juniverse.domain.models.notification.NotificationModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.domain.services.notification.NotificationService;
import juniverse.persistance.repositories.news.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final IdentityProvider identityProvider;
    private final NotificationService notificationService;

    public boolean addEvent(EventModel eventModel) {
        notificationService.notifyAllUsers(NotificationModel.builder()
                .isRead(false)
                .type("event")
                .content("new event is coming | " + eventModel.getTitle())
                .createdOn(LocalDateTime.now())
                .build());

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
