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
                int num = userService.createNewUser(tblUser);
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

    @GetMapping("/login/{login}/{rawPW}")
    public ResponseEntity<Integer> login(@PathVariable String login, @PathVariable String rawPW) {
        if (login != null && rawPW != null) {
            int res = userService.login(login, rawPW);
            if (res == 0) { // 0 = success
                return ResponseEntity.status(HttpStatus.OK).body(0);
            } else if (res == 2){ //2 = wrong login or PW
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2);
            } else if (res == 3) { // 3 = user blocked
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(3);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);  //1 = error within method
    }

    @PutMapping("/modifyUser/{login}")
    public ResponseEntity<Integer> modifyAccessClass(@RequestBody Tbl_user tblUser, @PathVariable String login) {
        try {
            if (areAnyFieldsNull(tblUser)) {
                int num = userService.modifyUser(tblUser, login);
                if (num == 0) { // 0 means success(inserted to db)
                    return ResponseEntity.status(HttpStatus.OK).body(0);
                } else if (num == 2) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2); //  2 means that ID already exists(not inserted to db)
                }
            }
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1); //  1 means that there is an error(not inserted to db)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @PutMapping("/changePassword/{login}/{rawPW}")
    public ResponseEntity<Integer> changePassword(@PathVariable String login, @PathVariable String rawPW){
        if (login != null && rawPW != null) {
            int num = userService.changePW(login, rawPW);
            if (num == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(0);
            } else if (num == 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @DeleteMapping("/removeUser/{login}")
    public ResponseEntity<Integer> removeUser(@PathVariable String login){
        if (login != null) {
            int num = userService.removeUser(login);
            if (num == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(0);
            } else if (num == 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
    }

    @PutMapping("/unblockUser/{login}")
    public ResponseEntity<Integer> getUnblocked(@PathVariable String login){
        if (login != null)  {
            int num = userService.unblockUser(login);
            if (num == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(0);
            } else if (num == 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
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


