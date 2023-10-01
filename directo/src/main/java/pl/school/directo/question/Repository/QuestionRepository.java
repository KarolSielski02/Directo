package pl.school.directo.question.Repository;

import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.school.directo.question.Model.Tbl_question;

@Repository
@Table(name = "tbl_question")
public interface QuestionRepository extends JpaRepository<Tbl_question, Integer> {
}
