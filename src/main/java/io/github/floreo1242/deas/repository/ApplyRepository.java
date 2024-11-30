package io.github.floreo1242.deas.repository;

import io.github.floreo1242.deas.domain.Apply;
import io.github.floreo1242.deas.domain.Event;
import io.github.floreo1242.deas.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Integer> {

    List<Apply> findByMember(Member member);

    Integer countByEvent(Event event);
}
