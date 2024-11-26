package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.DTO.request.CreateEventRequest;
import io.github.floreo1242.deas.domain.Event;
import io.github.floreo1242.deas.domain.EventStatus;
import io.github.floreo1242.deas.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public boolean createEvent(CreateEventRequest request) {
        try {
            LocalDateTime now = LocalDateTime.now();
            EventStatus eventStatus = EventStatus.PENDING;
            if (now.isAfter(request.getApplyStartTime()) && now.isBefore(request.getApplyEndTime())) {
                eventStatus = EventStatus.IN_PROGRESS;
            }
            Event event = Event.builder()
                    .name(request.getName())
                    .tag(request.getTag())
                    .description(request.getDescription())
                    .maxParticipant(request.getMaxParticipant())
                    .applyStartTime(request.getApplyStartTime())
                    .applyEndTime(request.getApplyEndTime())
                    .status(eventStatus)
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
}
