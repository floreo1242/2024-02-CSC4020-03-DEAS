package io.github.floreo1242.deas.domain;

import io.github.floreo1242.deas.domain.ids.EventLocationId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventLocation {

    @EmbeddedId
    private EventLocationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("locationId")
    @JoinColumn(name = "location_id")
    private Location location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder
    public EventLocation(EventLocationId id, Event event, Location location, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.event = event;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
