package io.github.floreo1242.deas.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Builder
    public Question(Event event, String content, QuestionType type) {
        this.event = event;
        this.content = content;
        this.type = type;
    }

    @OneToMany(mappedBy = "question")
    private List<Choice> choices = new ArrayList<>();

    @OneToOne(mappedBy = "question")
    private Answer answer;
}
