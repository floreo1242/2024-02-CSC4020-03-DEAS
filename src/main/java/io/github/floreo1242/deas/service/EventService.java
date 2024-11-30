package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.DTO.request.ApplyEventRequest;
import io.github.floreo1242.deas.DTO.request.CreateEventRequest;
import io.github.floreo1242.deas.domain.*;
import io.github.floreo1242.deas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;
    private final ChoiceRepository choiceRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public boolean createEvent(CreateEventRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Member member = memberRepository.findById(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            Event event = Event.builder()
                    .creator(member)
                    .name(request.getName())
                    .tag(request.getTag())
                    .description(request.getDescription())
                    .maxParticipant(request.getMaxParticipant())
                    .applyStartTime(request.getApplyStartTime())
                    .applyEndTime(request.getApplyEndTime())
                    .organizer(request.getOrganizer())
                    .contact(request.getContact())
                    .build();
            eventRepository.save(event);
            for (CreateEventRequest.QuestionRequest questionRequest : request.getQuestions()) {
                questionService.createQuestion(event, questionRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Event> getEventList() {
        return eventRepository.findAll();
    }

    public Event getEventById(Integer eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    @Transactional
    public boolean applyEvent(ApplyEventRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Member member = memberRepository.findById(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            Event event = getEventById(request.getEventId());
            Apply apply = Apply.builder()
                    .member(member)
                    .event(event)
                    .build();
            applyRepository.save(apply);
            if (request.getAnswers() != null) {
                request.getAnswers().forEach((questionId, answer) -> {
                    Question question = questionRepository.findById(questionId)
                            .orElseThrow(() -> new IllegalArgumentException("Question not found"));
                    QuestionType questionType = question.getType();
                    switch (questionType) {
                        case ESSAY -> {
                            Answer answerEntity = Answer.builder()
                                    .apply(apply)
                                    .question(question)
                                    .type(questionType)
                                    .answer(answer)
                                    .build();
                            answerRepository.save(answerEntity);
                        }
                        case CHOICE -> {
                            Choice choice = choiceRepository.findById(Integer.parseInt(answer))
                                    .orElseThrow(() -> new IllegalArgumentException("Choice not found"));
                            Answer answerEntity = Answer.builder()
                                    .apply(apply)
                                    .question(question)
                                    .type(questionType)
                                    .choice(choice)
                                    .build();
                            answerRepository.save(answerEntity);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
