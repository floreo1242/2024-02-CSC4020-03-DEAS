package io.github.floreo1242.deas.repository;

import io.github.floreo1242.deas.domain.Event;
import io.github.floreo1242.deas.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByCreator(Member creator);

    List<Event> findByAppliesMember(Member member);
}
