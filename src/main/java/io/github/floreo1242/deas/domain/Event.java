package io.github.floreo1242.deas.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Member creator;

    private String name;

    @Enumerated(EnumType.STRING)
    private EventTag tag;

    private String description;

    private Integer maxParticipant;

    private LocalDateTime applyStartTime;

    private LocalDateTime applyEndTime;

    private String organizer;

    private String contact;

    @Builder
    public Event(Member creator, String name, EventTag tag, String description, Integer maxParticipant, LocalDateTime applyStartTime, LocalDateTime applyEndTime, String organizer, String contact) {
        this.creator = creator;
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.maxParticipant = maxParticipant;
        this.applyStartTime = applyStartTime;
        this.applyEndTime = applyEndTime;
        this.organizer = organizer;
        this.contact = contact;
    }

    @OneToMany(mappedBy = "event")
    private List<Apply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventLocation> eventLocations = new ArrayList<>();
}
