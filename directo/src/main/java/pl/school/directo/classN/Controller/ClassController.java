package pl.school.directo.classN.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.classN.Model.Tbl_class;
import pl.school.directo.classN.Service.ClassService;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.common.Utils.ResponseUtils;

import java.util.List;

import static pl.school.directo.common.Enums.ResponseCodeEnums.*;
import static pl.school.directo.common.Utils.ValidateUtils.areAnyFieldsNull;

@RestController
@RequestMapping("/classController")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/createClass")
    public ResponseEntity<String> createClass(@RequestBody Tbl_class tbl_class) {
        try {
            if (areAnyFieldsNull(tbl_class)) {
                ResponseCodeEnums result = classService.createNewClass(tbl_class);
                return ResponseUtils.generateResponseEntity(result);
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name()); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
    }

    @PutMapping("/modifyClass/{newName}")
    ResponseEntity<String> modifyClass(@RequestBody Tbl_class tbl_class, @PathVariable String newName){
        try {
            if (areAnyFieldsNull(tbl_class)) {
                ResponseCodeEnums result  = classService.modifyClass(tbl_class, newName);
                return ResponseUtils.generateResponseEntity(result);
            }
        }catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_MODIFICATION.name()); //  1 means that there is an error(not inserted into db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_MODIFICATION.name());
    }

    @DeleteMapping("/removeClass/{className}")
    public ResponseEntity<String> removeClass(@PathVariable String className){
        ResponseCodeEnums result = classService.removeClass(className);
        return ResponseUtils.generateResponseEntity(result);
    }

    @GetMapping("/GetClass")
    public ResponseEntity<List<String>> getClasses(){
        List<String> list = classService.getClasses();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/GetClass/{className}")
    public ResponseEntity<String> getClassesByName(@PathVariable String className) {
        if (className != null) {
            String cn = classService.getClassById(className);
            return ResponseEntity.status(HttpStatus.OK).body(cn);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(VALIDATION_ERROR.name());
        }
    }
}

