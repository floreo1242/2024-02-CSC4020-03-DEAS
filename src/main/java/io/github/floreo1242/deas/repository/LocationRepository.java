package io.github.floreo1242.deas.repository;

import io.github.floreo1242.deas.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    @Query("""
              SELECT l FROM Location l
              LEFT JOIN EventLocation el ON el.location.id = l.id
              AND el.startTime < :end
              AND el.startTime > :start
              WHERE el.event.id IS NULL
            """)
    List<Location> findAvailableLocations(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
