package pl.school.directo.common.Utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class ValidateUtils {

    public static boolean areAnyFieldsNull(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj) == null) {
                return false; // returns false, if there is any null
            }
        }
        return true; // returns true if every field is true
    }
}
