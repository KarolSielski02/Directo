package pl.school.directo.access.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.access.Model.Tbl_access;
import pl.school.directo.access.Service.AccessService;

import static pl.school.directo.common.ValidateUtils.areAnyFieldsNull;

@RestController
@RequestMapping("/accessController")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/createAccessClass")
    public ResponseEntity<Integer> createAccessClass(@RequestBody Tbl_access tblAccess) {
        try {
            if (areAnyFieldsNull(tblAccess)) {
                if (accessService.createNewAccessClass(tblAccess) == 0) { // 0 means success(inserted to db)
                    return ResponseEntity.status(HttpStatus.CREATED).body(0);
                } else if (accessService.createNewAccessClass(tblAccess) == 2) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); //  2 means that ID already exists(not inserted to db)
                }
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @GetMapping("/getIds")
    public ResponseEntity<String> getIds(){
        return ResponseEntity.status(HttpStatus.OK).body(accessService.getIds());
    }

    @GetMapping("/getAccessClassById/{id}")
    public ResponseEntity<String> getAccessClassById(@PathVariable String id){
        if (id != null){
            String output = accessService.getAccessClassById(id);
            if (output != null){
                return ResponseEntity.status(HttpStatus.OK).body(output); // returns JSON obj of Tbl_access
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("2"); // 2 means that id doesn't exist
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("1"); // 1 means that id wasn't given so error
    }

    @PutMapping("/modifyAccessClass/{id}")
    public ResponseEntity<Integer> modifyAccessClass(@RequestBody Tbl_access tblAccess, @PathVariable String id){
        try {
            if (areAnyFieldsNull(tblAccess)) {
                if (accessService.modifyAccessClass(tblAccess, id) == 0) { // 0 means success(inserted to db)
                    return ResponseEntity.status(HttpStatus.OK).body(0);
                } else if (accessService.modifyAccessClass(tblAccess, id) == 2) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); //  2 means that ID already exists(not inserted to db)
                }
            }
        }catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @DeleteMapping("/removeAccessClass/{id}")
    public ResponseEntity<Integer> removeAccessClass(@PathVariable String id){
        if (id != null){
            if (accessService.removeAccessClass(id) == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(0); // 0 means success(deleted from db)
            } else if (accessService.removeAccessClass(id) == 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); //  2 means that ID does not exist(not inserted to db)
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(id wasn't given)
    }

}
