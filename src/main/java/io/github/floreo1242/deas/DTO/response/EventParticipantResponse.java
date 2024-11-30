package io.github.floreo1242.deas.DTO.response;

import io.github.floreo1242.deas.domain.Event;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventParticipantResponse {

    private Event event;

    private Integer participantCount;
}
