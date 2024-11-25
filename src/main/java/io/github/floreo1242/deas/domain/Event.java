package io.github.floreo1242.deas.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private EventTag tag;

    private String description;

    private Integer maxParticipant;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private LocalDateTime applyStartTime;

    private LocalDateTime applyEndTime;

    private String organizer;

    private String contact;

    @OneToMany(mappedBy = "event")
    private List<Apply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<Question> questions = new ArrayList<>();
}
