package io.github.floreo1242.deas.DTO.response;

import io.github.floreo1242.deas.domain.Answer;
import io.github.floreo1242.deas.domain.Apply;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetApplyDetailResponse {

    private Apply apply;

    private List<Answer> answerList;
}
