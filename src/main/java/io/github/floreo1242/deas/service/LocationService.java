package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.DTO.response.LocationResponse;
import io.github.floreo1242.deas.domain.Location;
import io.github.floreo1242.deas.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<LocationResponse> getAvailableLocations(LocalDateTime start, LocalDateTime end) {
        List<Location> locations = locationRepository.findAvailableLocations(start, end);
        return locations.stream()
                .map(location -> LocationResponse.builder()
                        .id(location.getId())
                        .capacity(location.getCapacity())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
