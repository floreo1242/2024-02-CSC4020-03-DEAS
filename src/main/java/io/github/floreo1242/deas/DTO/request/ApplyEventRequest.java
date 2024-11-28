package io.github.floreo1242.deas.DTO.request;

import lombok.Data;

import java.util.Map;

@Data
public class ApplyEventRequest {

    private Integer eventId;

    private Map<Integer, String> answers;
}
