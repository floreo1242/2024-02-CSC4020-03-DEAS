package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.DTO.request.ApplyEventRequest;
import io.github.floreo1242.deas.DTO.request.CreateEventRequest;
import io.github.floreo1242.deas.DTO.response.EventParticipantResponse;
import io.github.floreo1242.deas.domain.*;
import io.github.floreo1242.deas.domain.ids.EventLocationId;
import io.github.floreo1242.deas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final LocationRepository locationRepository;
    private final EventLocationRepository eventLocationRepository;

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
            Location location = locationRepository.findById(request.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found"));
            EventLocation eventLocation = EventLocation.builder()
                    .id(EventLocationId.builder()
                            .eventId(event.getId())
                            .locationId(request.getLocationId())
                            .build())
                    .event(event)
                    .location(location)
                    .startTime(request.getEventStartTime())
                    .endTime(request.getEventEndTime())
                    .build();
            eventLocationRepository.save(eventLocation);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean editEvent(CreateEventRequest request, Integer eventId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            event.setName(request.getName());
            event.setTag(request.getTag());
            event.setDescription(request.getDescription());
            event.setMaxParticipant(request.getMaxParticipant());
            event.setOrganizer(request.getOrganizer());
            event.setContact(request.getContact());
            eventRepository.save(event);
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

    public EventLocation getLocationByEvent(Integer eventId) {
        Event event = getEventById(eventId);
        return eventLocationRepository.findByEvent(event);
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

    public List<EventParticipantResponse> getEventByCreator(String creatorId) {
        Member creator = memberRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        List<Event> events = eventRepository.findByCreator(creator);
        return events.stream()
                .map(event -> EventParticipantResponse.builder()
                        .event(event)
                        .participantCount(applyRepository.countByEvent(event))
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<Event> getEventByApply(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        List<Apply> applies = applyRepository.findByMember(member);
        return applies.stream()
                .map(Apply::getEvent)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteEvent(Integer eventId) {
        try {
            eventRepository.deleteById(eventId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
