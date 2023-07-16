package pl.school.directo.user.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.school.directo.user.Model.Tbl_user;
import pl.school.directo.user.Service.UserService;

import static pl.school.directo.common.ValidateUtils.areAnyFieldsNull;

@RestController
@RequestMapping("/userController")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<Integer> createUser(@RequestBody Tbl_user tblUser) {
        try {
            if (areAnyFieldsNull(tblUser)) {
                if (userService.createNewUser(tblUser) == 0) {
                    return ResponseEntity.status(HttpStatus.CREATED).body(0); // 0 means success(inserted into db)
                } else if (userService.createNewUser(tblUser) == 2) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); // 2 means login already exists
                }
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @GetMapping("/login")
    public ResponseEntity<Integer> login(@RequestParam String login, @RequestParam String rawPW) { //alternatively change to @PathVariable
        if (login != null && rawPW != null) {
            if (userService.login(login, rawPW) == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(0);
            } else if (userService.login(login, rawPW) == 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @PutMapping("/modifyUser/{login}")
    public ResponseEntity<Integer> modifyAccessClass(@RequestBody Tbl_user tblUser, @PathVariable String login) {
        try {
            if (areAnyFieldsNull(tblUser)) {
                if (userService.modifyUser(tblUser, login) == 0) { // 0 means success(inserted to db)
                    return ResponseEntity.status(HttpStatus.OK).body(0);
                } else if (userService.modifyUser(tblUser, login) == 2) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); //  2 means that ID already exists(not inserted to db)
                }
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    //TODO add Password change, Delete User, GetUserByLogin and maybe other things
}
