package pl.school.directo.question.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.school.directo.question.Service.QuestionService;

@RestController
@RequestMapping("/questionController")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

//    @PostMapping("/createQuestion")
//    public ResponseEntity<String> createQuestion(@RequestBody Tbl_question tblQuestion){
//        try {
//            if (areAnyFieldsNull(tblQuestion)) {
//                ResponseCodeEnums result = questionService.createQuestion(tblQuestion);
//                return ResponseUtils.generateResponseEntity(result);
//            }
//        } catch (IllegalAccessException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name()); //  1 means that there is an error(not inserted to db)
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
//    }
}

