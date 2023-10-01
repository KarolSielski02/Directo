package pl.school.directo.stdnt.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.common.Utils.ResponseUtils;
import pl.school.directo.stdnt.Model.Tbl_stdnt;
import pl.school.directo.stdnt.Service.StdntService;

import java.util.List;

import static pl.school.directo.common.Enums.ResponseCodeEnums.*;
import static pl.school.directo.common.Utils.ValidateUtils.areAnyFieldsNull;

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
                ResponseCodeEnums result = stdntService.createNewStdnt(tblStdnt);
                return ResponseUtils.generateResponseEntity(result);
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name()); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
    }

    @PutMapping("/modifyUser/{pesel}")
    public ResponseEntity <String> modifyStdnt(@RequestBody Tbl_stdnt tblStdnt, @PathVariable String pesel){
        try {
            if (areAnyFieldsNull(tblStdnt)) {
                ResponseCodeEnums result = stdntService.modifyStdnt(tblStdnt, pesel);
                return ResponseUtils.generateResponseEntity(result);
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_MODIFICATION.name()); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_MODIFICATION.name());
    }

    @DeleteMapping("/removeStdnt/{pesel}")
    public ResponseEntity<String> removeStdnt(@PathVariable String pesel){
            ResponseCodeEnums result = stdntService.removeStdnt(pesel);
            return ResponseUtils.generateResponseEntity(result);
    }

    @GetMapping("/GetStdnt")
    public ResponseEntity<List<String>> getStdnts(){
        List<String> list = stdntService.getStdnts();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/GetStdnt/{pesel}")
    public ResponseEntity<String> getStdnt(@PathVariable String pesel){
        if (pesel != null) {
            String stdnt = stdntService.getStdntByPesel(pesel);
            return ResponseEntity.status(HttpStatus.OK).body(stdnt);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(VALIDATION_ERROR.name());
        }
    }
}
