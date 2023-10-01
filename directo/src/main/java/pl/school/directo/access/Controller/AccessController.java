package pl.school.directo.access.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.access.Model.Tbl_access;
import pl.school.directo.access.Service.AccessService;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.common.Utils.ResponseUtils;

import static pl.school.directo.common.Utils.ValidateUtils.areAnyFieldsNull;
import static pl.school.directo.common.Enums.ResponseCodeEnums.*;

@RestController
@RequestMapping("/accessController")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/createAccessClass")
    public ResponseEntity<String> createAccessClass(@RequestBody Tbl_access tblAccess) {
        try {
            if (areAnyFieldsNull(tblAccess)) {
                ResponseCodeEnums result = accessService.createNewAccessClass(tblAccess);
                return ResponseUtils.generateResponseEntity(result);
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name()); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OBJ_NOT_FOUND.name()); // 2 means that id doesn't exist
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OBJ_NOT_FOUND.name()); // 1 means that id wasn't given so error
    }

    @PutMapping("/modifyAccessClass/{id}")
    public ResponseEntity<String> modifyAccessClass(@RequestBody Tbl_access tblAccess, @PathVariable String id){
        try {
            if (areAnyFieldsNull(tblAccess)) {
                ResponseCodeEnums result = accessService.modifyAccessClass(tblAccess, id);
                return ResponseUtils.generateResponseEntity(result);
            }
        }catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(METHOD_ERROR.name()); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(METHOD_ERROR.name());
    }

    @DeleteMapping("/removeAccessClass/{id}")
    public ResponseEntity<String> removeAccessClass(@PathVariable String id){
        if (id != null){
            ResponseCodeEnums result = accessService.removeAccessClass(id);
            return ResponseUtils.generateResponseEntity(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(METHOD_ERROR.name()); //  1 means that there is an error(id wasn't given)
    }

}
