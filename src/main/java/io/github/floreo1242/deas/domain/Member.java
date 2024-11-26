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
public class Member {

    @Id
    private String id;

    private String password;

    private String name;

    private String affiliation;

    private String contact;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Builder
    public Member(String id, String password, String name, String affiliation, String contact, MemberType type) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.affiliation = affiliation;
        this.contact = contact;
        this.type = type;
    }

    @OneToMany(mappedBy = "member")
    private List<Apply> applies = new ArrayList<>();
}
