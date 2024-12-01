package io.github.floreo1242.deas.DTO.request;

import io.github.floreo1242.deas.domain.EventTag;
import io.github.floreo1242.deas.domain.QuestionType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateEventRequest {

    private String name;

    private EventTag tag;

    private String description;

    private Integer maxParticipant;

    private LocalDateTime applyStartTime;

    private LocalDateTime applyEndTime;

    private LocalDateTime eventStartTime;

    private LocalDateTime eventEndTime;

    private String organizer;

    private String contact;

    private String locationId;

    private List<QuestionRequest> questions;

    @Data
    public static class QuestionRequest {

        private String content;

        private QuestionType type;

        private List<String> choice;
    }
}
