package pl.school.directo.stdnt.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.common.ResponseCode;
import pl.school.directo.common.StatusUtils;
import pl.school.directo.stdnt.Model.Tbl_stdnt;
import pl.school.directo.stdnt.Service.StdntService;

import static pl.school.directo.common.ResponseCode.*;
import static pl.school.directo.common.ValidateUtils.areAnyFieldsNull;

@RestController
@RequestMapping("/stdntController")
public class StdntController {

    private final StdntService stdntService;

    public StdntController(StdntService stdntService) {
        this.stdntService = stdntService;
    }

    @PostMapping("/createStdnt")
    public ResponseEntity<String> createStdnt(@RequestBody Tbl_stdnt tblStdnt){
        try {
            if (areAnyFieldsNull(tblStdnt)) {
                ResponseCode result = stdntService.createNewStdnt(tblStdnt);
                return ResponseEntity.status(StatusUtils.mapHttpStatus(result)).body(result.name());
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name()); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
    }

    @PutMapping("/modifyUser/{pesel}")
    public ResponseEntity <Integer> modifyStdnt(@RequestBody Tbl_stdnt tblStdnt, @PathVariable String pesel){
        try {
            if (areAnyFieldsNull(tblStdnt)) {
                int result = stdntService.modifyStdnt(tblStdnt, pesel);
                if (result == 0) { // 0 means success(inserted to db)
                    return ResponseEntity.status(HttpStatus.OK).body(0);
                } else if (result == 2) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); //  2 means that ID already exists(not inserted to db)
                }
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @DeleteMapping("/removeStdnt/{pesel}")
    public ResponseEntity<Integer> removeStdnt(@PathVariable String pesel){
        if (pesel != null){
            int result = stdntService.removeStdnt(pesel);
            if (result == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(0);
            } else if (result == 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }
}
