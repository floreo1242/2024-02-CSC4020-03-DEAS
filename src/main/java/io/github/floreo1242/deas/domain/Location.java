package io.github.floreo1242.deas.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    private String id;

    private Integer capacity;

    @OneToMany(mappedBy = "location")
    private List<EventLocation> eventLocations = new ArrayList<>();
}
