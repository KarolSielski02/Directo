package pl.school.directo.question.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.question.Repository.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

//    public ResponseCodeEnums createQuestion(Tbl_question tblQuestion) {
//    boolean questionExists = isQuestionExists(tblQuestion.getQuestion(), tblQuestion.getCategory());
//    }
}
