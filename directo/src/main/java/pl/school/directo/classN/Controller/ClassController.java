package pl.school.directo.classN.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.classN.Model.Tbl_class;
import pl.school.directo.classN.Service.ClassService;

import java.util.List;

import static pl.school.directo.common.ValidateUtils.areAnyFieldsNull;

@RestController
@RequestMapping("/classController")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/createClass")
    public ResponseEntity<Integer> createUser(@RequestBody Tbl_class tbl_class) {
        try {
            if (areAnyFieldsNull(tbl_class)) {
                int num = classService.createNewClass(tbl_class);
                if (num == 0) {
                    return ResponseEntity.status(HttpStatus.CREATED).body(0); // 0 means success(inserted into db)
                } else if (num == 2) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); // 2 means login already exists
                }
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @PutMapping("/modifyClass/{newName}")
    ResponseEntity<Integer> modifyClass(@RequestBody Tbl_class tbl_class, @PathVariable String newName){
        try {
            if (areAnyFieldsNull(tbl_class)) {
                int num = classService.modifyClass(tbl_class, newName);
                if (num == 0) { // 0 means success(change in db)
                    return ResponseEntity.status(HttpStatus.OK).body(0);
                } else if (num == 2) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); //  2 means that ID doesn't exist(not inserted into db)
                }
            }
        }catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(not inserted into db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @DeleteMapping("/removeClass/{className}")
    public ResponseEntity<Integer> removeClass(@PathVariable String className){
        if (className != null){
            int num = classService.removeClass(className);
            if (num == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(0);
            } else if (num == 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @GetMapping("/GetClass")
    public ResponseEntity<List<String>> getClasses(){
        List<String> list = classService.getClasses();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

/*
    I don't need that prob

    @GetMapping("/GetClass/{className}")
    public ResponseEntity<String> getClassesByName(@PathVariable String className){
        if (className != null){
            String classN = classService.getClassN(className);
            if (classN != null) {
                return ResponseEntity.status(HttpStatus.OK).body(classN)
            }
        }
    }

 */
}

