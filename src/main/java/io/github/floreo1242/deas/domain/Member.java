package io.github.floreo1242.deas.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String password;

    private String name;

    private String affiliation;

    private String contact;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @OneToMany(mappedBy = "member")
    private List<Apply> applies = new ArrayList<>();
}
