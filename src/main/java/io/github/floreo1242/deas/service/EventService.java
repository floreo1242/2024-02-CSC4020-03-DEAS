package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.DTO.request.CreateEventRequest;
import io.github.floreo1242.deas.domain.Event;
import io.github.floreo1242.deas.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public boolean createEvent(CreateEventRequest request) {
        try {
            Event event = Event.builder()
                    .name(request.getName())
                    .tag(request.getTag())
                    .description(request.getDescription())
                    .maxParticipant(request.getMaxParticipant())
                    .applyStartTime(request.getApplyStartTime())
                    .applyEndTime(request.getApplyEndTime())
                    .organizer(request.getOrganizer())
                    .contact(request.getContact())
                    .build();
            eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Event> getEventList() {
        return eventRepository.findAll();
    }

    public Event getEventById(Integer eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }
}
