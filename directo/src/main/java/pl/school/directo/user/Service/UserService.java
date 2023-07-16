package pl.school.directo.user.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.user.Model.Tbl_user;
import pl.school.directo.user.Repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static pl.school.directo.common.PwUtils.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int createNewUser(Tbl_user tblUser) {
        if (!isLoginExists(tblUser.getLogin())){
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
            if (matchPW(rawPW, userRepository.getPW(login))) {
                return 0;
            } else {
                return 2;
            }
        } else if(!isLoginExists(login)){
            return 2;
        }
        return 1;
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
        if (isLoginExists(login)){
            userRepository.modifyUser(tblUser.getLogin(), tblUser.getTbl_access_access_class(), login);
            return 0;
        } else if (!isLoginExists(login)) {
            return 2;
        }
        return 1;
    }
}
