package io.github.floreo1242.deas.controller;

import io.github.floreo1242.deas.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/api/available-locations")
    public ResponseEntity<?> availableLocations(@RequestParam LocalDateTime startTime, LocalDateTime endTime) {
        return ResponseEntity.ok(locationService.getAvailableLocations(startTime, endTime));
    }

}
