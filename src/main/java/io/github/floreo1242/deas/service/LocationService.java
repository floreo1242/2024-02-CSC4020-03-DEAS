package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.domain.Location;
import io.github.floreo1242.deas.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getAvailableLocations(LocalDateTime start, LocalDateTime end) {
        return locationRepository.findAvailableLocations(start, end);
    }
}
