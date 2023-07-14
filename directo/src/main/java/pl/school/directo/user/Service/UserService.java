package pl.school.directo.user.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.user.Model.Tbl_user;
import pl.school.directo.user.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int createNewUser(Tbl_user tblUser) {
    }
}
