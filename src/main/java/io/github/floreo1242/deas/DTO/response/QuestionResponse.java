package io.github.floreo1242.deas.DTO.response;

import io.github.floreo1242.deas.domain.Choice;
import io.github.floreo1242.deas.domain.QuestionType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResponse {

    private Integer id;

    private String content;

    private QuestionType type;

    private List<Choice> choices;
}
