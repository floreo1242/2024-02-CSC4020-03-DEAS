package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.DTO.request.CreateEventRequest;
import io.github.floreo1242.deas.domain.Choice;
import io.github.floreo1242.deas.domain.Event;
import io.github.floreo1242.deas.domain.Question;
import io.github.floreo1242.deas.domain.QuestionType;
import io.github.floreo1242.deas.repository.ChoiceRepository;
import io.github.floreo1242.deas.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    @Transactional
    public void createQuestion(Event event, CreateEventRequest.QuestionRequest questionRequest) {

        Question question = Question.builder()
                .event(event)
                .type(questionRequest.getType())
                .content(questionRequest.getContent())
                .build();
        questionRepository.save(question);
        if (questionRequest.getType().equals(QuestionType.CHOICE)) {
            for (String choice : questionRequest.getChoice()) {
                if (choice != null && !choice.isEmpty()) {
                    choiceRepository.save(Choice.builder()
                            .question(question)
                            .content(choice)
                            .build()
                    );
                }
            }
        }
    }
}
