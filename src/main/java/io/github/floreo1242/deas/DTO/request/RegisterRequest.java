package io.github.floreo1242.deas.DTO.request;

import io.github.floreo1242.deas.domain.MemberType;
import lombok.Data;

@Data
public class RegisterRequest {

    private String id;

    private String password;

    private String name;

    private String affiliation;

    private String contact;

    private MemberType type;
}
