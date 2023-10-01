package pl.school.directo.question.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.common.Utils.ResponseUtils;
import pl.school.directo.question.Model.Tbl_question;
import pl.school.directo.question.Service.QuestionService;

import static pl.school.directo.common.Enums.ResponseCodeEnums.FAILED_CREATION;
import static pl.school.directo.common.Utils.ValidateUtils.areAnyFieldsNull;

@RestController
@RequestMapping("/questionController")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/createQuestion")
    public ResponseEntity<String> createQuestion(@RequestBody Tbl_question tblQuestion){
        try {
            if (areAnyFieldsNull(tblQuestion)) {
                ResponseCodeEnums result = questionService.createQuestion(tblQuestion);
                return ResponseUtils.generateResponseEntity(result);
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name()); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
    }
    }
}
