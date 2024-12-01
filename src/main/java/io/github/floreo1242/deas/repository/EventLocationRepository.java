package io.github.floreo1242.deas.repository;

import io.github.floreo1242.deas.domain.Event;
import io.github.floreo1242.deas.domain.EventLocation;
import io.github.floreo1242.deas.domain.ids.EventLocationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, EventLocationId> {

    EventLocation findByEvent(Event event);
}
