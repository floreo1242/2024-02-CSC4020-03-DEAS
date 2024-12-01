package io.github.floreo1242.deas.DTO.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationResponse {

    private String id;

    private Integer capacity;
}
