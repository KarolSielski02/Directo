package pl.school.directo.user.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.access.Repository.AccessRepository;
import pl.school.directo.user.Model.Tbl_user;
import pl.school.directo.user.Repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static pl.school.directo.common.PwUtils.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccessRepository accessRepository;

    public UserService(UserRepository userRepository, AccessRepository accessRepository) {
        this.userRepository = userRepository;
        this.accessRepository = accessRepository;
    }

    public int createNewUser(Tbl_user tblUser) {
        if (!isLoginExists(tblUser.getLogin()) && accessRepository.getIDs().contains(tblUser.getTbl_access_access_class())){
            userRepository.createUser(tblUser.getLogin(),
                    hashPw(tblUser.getPassword()),
                    tblUser.getTbl_access_access_class());
            return 0;
        } else if (isLoginExists(tblUser.getLogin())) {
            return 2;
        }
        return 1;
    }

    public int login(String login, String rawPW){
        if (isLoginExists(login)) {
            if (!userRepository.getIsBlocked(login)) {
                if (matchPW(rawPW, userRepository.getPW(login))) {
                    return 0; // 0 = success
                } else {
                    int num = userRepository.getUnsucLogs(login) + 1;
                    userRepository.increaseUnsucLogs(login, num);
                    if (num >= 5){
                        userRepository.getBlocked(login);
                    }
                    return 2; // 2 = wrong login or PW (here wrong PW)
                }
            } else {
                return 3; // 3 = user blocked
            }
        } else if(!isLoginExists(login)){
            return 2; //2 = wrong login or PW (here non-existing email)
        }
        return 1; //3 = error within method
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

    public int modifyUser(Tbl_user tblUser, String login) {
        boolean newLoginExists = isLoginExists(tblUser.getLogin());
        boolean currentLoginExists = isLoginExists(login);

        if (!Objects.equals(tblUser.getLogin(), login)) {
            if (currentLoginExists && !newLoginExists) {
                userRepository.modifyUserLogin(tblUser.getLogin(), tblUser.getTbl_access_access_class(), login);
                return 0;
            } else {
                return 2;
            }
        } else {
            if (currentLoginExists) {
                userRepository.modifyUserNoLogin(login, tblUser.getTbl_access_access_class());
                return 0;
            } else {
                return 2;
            }
        }
    }

    public int changePW(String login, String rawPW) {
        if (isLoginExists(login)) {
            userRepository.changePW(login, hashPw(rawPW));
            return 0;
        } else if(!isLoginExists(login)){
            return 2;
        }
        return 1;
    }

    public int removeUser(String login) {
        if (isLoginExists(login)) {
            userRepository.removeUser(login);
            return 0;
        } else if(!isLoginExists(login)){
            return 2;
        }
        return 1;
    }

    public int unblockUser(String login) {
        if (isLoginExists(login)) {
            userRepository.getUnBlocked(login);
            return 0;
        } else if(!isLoginExists(login)){
            return 2;
        }
        return 1;
    }

    public Tbl_user getUser(String login) {
        if (isLoginExists(login)){
            return userRepository.getUser(login);
        } else  {
            return null;
        }
    }
}
