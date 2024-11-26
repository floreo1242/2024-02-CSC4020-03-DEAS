package io.github.floreo1242.deas.repository;

import io.github.floreo1242.deas.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
