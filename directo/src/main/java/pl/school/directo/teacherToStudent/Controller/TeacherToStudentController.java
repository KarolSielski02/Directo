package pl.school.directo.teacherToStudent.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.common.Utils.ResponseUtils;
import pl.school.directo.teacherToStudent.Model.Assoc_teacher_to_student;
import pl.school.directo.teacherToStudent.Service.TeacherToStudentService;

import static pl.school.directo.common.Enums.ResponseCodeEnums.*;
import static pl.school.directo.common.Utils.ValidateUtils.areAnyFieldsNull;

@RestController
@RequestMapping("/TTSController")
public class TeacherToStudentController {

    private final TeacherToStudentService teacherToStudentService;

    public TeacherToStudentController(TeacherToStudentService teacherToStudentService) {
        this.teacherToStudentService = teacherToStudentService;
    }

    @PostMapping("/createTTSConnection")
    public ResponseEntity<String> createTTSConnection(@RequestBody Assoc_teacher_to_student teacherToStudent){
        try {
            if (areAnyFieldsNull(teacherToStudent)){
                ResponseCodeEnums result = teacherToStudentService.createTTSConnection(teacherToStudent);
                return ResponseUtils.generateResponseEntity(result);
            }
        } catch (IllegalAccessException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
    }

    @DeleteMapping("/removeTTSConnection/{id}")
    public ResponseEntity<String> removeTTSConnection(@PathVariable int id){
        return ResponseUtils.generateResponseEntity(teacherToStudentService.removeTTSConnection(id));
    }
}
