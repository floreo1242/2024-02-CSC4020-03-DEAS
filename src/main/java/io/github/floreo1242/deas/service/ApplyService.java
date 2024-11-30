package io.github.floreo1242.deas.service;

import io.github.floreo1242.deas.DTO.response.GetApplyDetailResponse;
import io.github.floreo1242.deas.domain.Answer;
import io.github.floreo1242.deas.domain.Apply;
import io.github.floreo1242.deas.domain.Event;
import io.github.floreo1242.deas.repository.AnswerRepository;
import io.github.floreo1242.deas.repository.ApplyRepository;
import io.github.floreo1242.deas.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final EventRepository eventRepository;
    private final AnswerRepository answerRepository;

    public List<GetApplyDetailResponse> getApplyDetails(Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        List<Apply> applies = applyRepository.findByEvent(event);
        return applies.stream()
                .map(apply -> {
                    List<Answer> answerList = answerRepository.findByApply(apply);
                    if (answerList == null) {
                        answerList = Collections.emptyList(); // Assign an empty list if null
                    }
                    return GetApplyDetailResponse.builder()
                            .apply(apply)
                            .answerList(answerList)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
