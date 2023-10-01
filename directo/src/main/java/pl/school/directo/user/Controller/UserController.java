package pl.school.directo.user.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.common.Utils.ResponseUtils;
import pl.school.directo.user.Model.Tbl_user;
import pl.school.directo.user.Service.UserService;

import static pl.school.directo.common.Enums.ResponseCodeEnums.*;
import static pl.school.directo.common.Utils.ValidateUtils.areAnyFieldsNull;

@RestController
@RequestMapping("/userController")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody Tbl_user tblUser) {
        try {
            if (areAnyFieldsNull(tblUser)) {
                ResponseCodeEnums result = userService.createNewUser(tblUser);
                return ResponseUtils.generateResponseEntity(result);
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_CREATION.name());
    }

    @GetMapping("/login/{login}/{rawPW}")
    public ResponseEntity<String> login(@PathVariable String login, @PathVariable String rawPW) {
        if (login != null && rawPW != null) {
            ResponseCodeEnums result = userService.login(login, rawPW);
            return ResponseUtils.generateResponseEntity(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(METHOD_ERROR.name());  //1 = error within method
    }

    @PutMapping("/modifyUser/{login}")
    public ResponseEntity<String> modifyAccessClass(@RequestBody Tbl_user tblUser, @PathVariable String login) {
        try {
            if (areAnyFieldsNull(tblUser)) {
                ResponseCodeEnums result = userService.modifyUser(tblUser, login);
                return ResponseUtils.generateResponseEntity(result);
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(METHOD_ERROR.name()); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(METHOD_ERROR.name());
    }

    @PutMapping("/changePassword/{login}/{rawPW}")
    public ResponseEntity<String> changePassword(@PathVariable String login, @PathVariable String rawPW){
        if (login != null && rawPW != null) {
            ResponseCodeEnums result = userService.changePW(login, rawPW);
            return ResponseUtils.generateResponseEntity(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_MODIFICATION.name());
    }

    @DeleteMapping("/removeUser/{login}")
    public ResponseEntity<String> removeUser(@PathVariable String login){
        if (login != null) {
            ResponseCodeEnums result = userService.removeUser(login);
            return ResponseUtils.generateResponseEntity(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_REMOVAL.name());
    }

    @PutMapping("/unblockUser/{login}")
    public ResponseEntity<String> getUnblocked(@PathVariable String login){
        if (login != null)  {
            ResponseCodeEnums result = userService.unblockUser(login);
            return ResponseUtils.generateResponseEntity(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILED_MODIFICATION.name());
    }

    @GetMapping("/GetUserByLogin/{login}")
    private ResponseEntity<Tbl_user> getUserByLogin(@PathVariable String login){
        if (login != null)  {
            Tbl_user user = userService.getUser(login);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(user); // Success
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Tbl_user()); //Bad Request
    }

}


