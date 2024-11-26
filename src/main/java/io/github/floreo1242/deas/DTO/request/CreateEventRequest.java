package io.github.floreo1242.deas.DTO.request;

import io.github.floreo1242.deas.domain.EventTag;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequest {

    private String name;

    private EventTag tag;

    private String description;

    private Integer maxParticipant;

    private LocalDateTime applyStartTime;

    private LocalDateTime applyEndTime;

    private String organizer;

    private String contact;
}
