package io.github.floreo1242.deas.repository;

import io.github.floreo1242.deas.domain.Answer;
import io.github.floreo1242.deas.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByApply(Apply apply);
}
