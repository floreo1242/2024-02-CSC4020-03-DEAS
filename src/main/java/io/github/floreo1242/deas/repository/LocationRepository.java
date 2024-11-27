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
              WHERE l.id NOT IN (
                  SELECT el.location.id FROM EventLocation el
                  WHERE (el.startTime < :end AND el.endTime > :start)
              )
            """)
    List<Location> findAvailableLocations(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
