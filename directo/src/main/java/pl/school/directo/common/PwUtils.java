package pl.school.directo.common;

import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;


@Component
public class PwUtils {

    static Pbkdf2PasswordEncoder pdkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("tooth", 16, 310000, 128);

    public static String hashPw(String password){
        return (pdkdf2PasswordEncoder.encode(password));
    }
    public static boolean matchPW(String rawPW, String hashedPW){
        if (pdkdf2PasswordEncoder.matches(rawPW, hashedPW)){
            return true; //success
        }else {
            return false; //failure
        }
    }
}
