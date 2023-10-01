package pl.school.directo.user.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.access.Repository.AccessRepository;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.user.Model.Tbl_user;
import pl.school.directo.user.Repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static pl.school.directo.common.Utils.PwUtils.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccessRepository accessRepository;

    public UserService(UserRepository userRepository, AccessRepository accessRepository) {
        this.userRepository = userRepository;
        this.accessRepository = accessRepository;
    }

    public ResponseCodeEnums createNewUser(Tbl_user tblUser) {
        boolean isLoginExists = isLoginExists(tblUser.getLogin());
        boolean isAccessClassExists = accessRepository.getIDs().contains(tblUser.getTbl_access_access_class());

        if (!isLoginExists && isAccessClassExists){
            userRepository.createUser(tblUser.getLogin(),
                    hashPw(tblUser.getPassword()),
                    tblUser.getTbl_access_access_class());
            return ResponseCodeEnums.SUCCESS_CREATED;
        } else if (isLoginExists) {
            return ResponseCodeEnums.CONFLICT_ID_EXISTS;
        }
        return ResponseCodeEnums.FAILED_CREATION;
    }

    public ResponseCodeEnums login(String login, String rawPW){
        if (isLoginExists(login)) {
            if (!userRepository.getIsBlocked(login)) {
                if (matchPW(rawPW, userRepository.getPW(login))) {
                    return ResponseCodeEnums.SUCCESS_LOGIN; // 0 = success
                } else {
                    int num = userRepository.getUnsucLogs(login) + 1;
                    userRepository.increaseUnsucLogs(login, num);
                    if (num >= 5){
                        userRepository.getBlocked(login);
                    }
                    return ResponseCodeEnums.FAILED_LOGIN; // 2 = wrong login or PW (here wrong PW)
                }
            } else {
                return ResponseCodeEnums.BLOCKED; // 3 = user blocked
            }
        } else if(!isLoginExists(login)){
            return ResponseCodeEnums.FAILED_LOGIN; //2 = wrong login or PW (here non-existing email)
        }
        return ResponseCodeEnums.METHOD_ERROR; //3 = error within method
    }

    public boolean isLoginExists(String login){
        List<String> stringList = userRepository.getLogins();
        for (String id: stringList) {
            if (Objects.equals(login, id)){
                return true;
            }
        }
        return false;
    }

    public ResponseCodeEnums modifyUser(Tbl_user tblUser, String login) {
        boolean newLoginExists = isLoginExists(tblUser.getLogin());
        boolean currentLoginExists = isLoginExists(login);
        boolean loginsEqual = Objects.equals(tblUser.getLogin(), login);

        if (!loginsEqual) {
            if (currentLoginExists && !newLoginExists) {
                userRepository.modifyUserLogin(tblUser.getLogin(), tblUser.getTbl_access_access_class(), login);
                return ResponseCodeEnums.SUCCESS_MODIFIED;
            } else {
                return ResponseCodeEnums.FAILED_MODIFICATION;
            }
        } else {
            if (currentLoginExists) {
                userRepository.modifyUserNoLogin(login, tblUser.getTbl_access_access_class());
                return ResponseCodeEnums.SUCCESS_MODIFIED;
            } else {
                return ResponseCodeEnums.FAILED_MODIFICATION;
            }
        }
    }

    public ResponseCodeEnums changePW(String login, String rawPW) {
        if (isLoginExists(login)) {
            userRepository.changePW(login, hashPw(rawPW));
            return ResponseCodeEnums.SUCCESS_MODIFIED;
        } else if(!isLoginExists(login)){
            return ResponseCodeEnums.FAILED_MODIFICATION;
        }
        return ResponseCodeEnums.METHOD_ERROR;
    }

    public ResponseCodeEnums removeUser(String login) {
        boolean isLoginExists = isLoginExists(login);
        if (isLoginExists) {
            userRepository.removeUser(login);
            return ResponseCodeEnums.SUCCESS_REMOVED;
        } else {
            return ResponseCodeEnums.FAILED_REMOVAL;
        }
    }

    public ResponseCodeEnums unblockUser(String login) {
        boolean isLoginExists = isLoginExists(login);
        if (isLoginExists) {
            userRepository.getUnBlocked(login);
            return ResponseCodeEnums.SUCCESS_MODIFIED;
        } else {
            return ResponseCodeEnums.FAILED_MODIFICATION;
        }
    }

    public Tbl_user getUser(String login) {
        if (isLoginExists(login)){
            return userRepository.getUser(login);
        } else  {
            return null;
        }
    }
}
