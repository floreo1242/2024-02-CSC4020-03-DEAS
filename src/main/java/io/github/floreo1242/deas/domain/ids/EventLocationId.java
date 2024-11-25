package io.github.floreo1242.deas.domain.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventLocationId implements Serializable {

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "location_id")
    private String locationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventLocationId that = (EventLocationId) o;
        return eventId.equals(that.eventId) && locationId.equals(that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, locationId);
    }
}