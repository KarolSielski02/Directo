package pl.school.directo.classN.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.classN.Model.Tbl_class;
import pl.school.directo.classN.Repository.ClassRepository;

import java.util.List;
import java.util.Objects;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }


    public int createNewClass(Tbl_class tblClass) {
        if (!isClassExists(tblClass.getClass_name())){
            classRepository.createClass(tblClass.getClass_name());
            return 0;
        } else if (isClassExists(tblClass.getClass_name())) {
            return 2;
        }
        return 1;
    }

    private boolean isClassExists(String class_name) {
        List<String> stringList = classRepository.getClasses();
        for (String id: stringList) {
            if (Objects.equals(class_name, id)){
                return true;
            }
        }
        return false;
    }

    public int modifyClass(Tbl_class tblClass, String newName) {
        if (isClassExists(tblClass.getClass_name()) && !isClassExists(newName)){
            classRepository.modifyClass(tblClass.getClass_name(), newName);
            return 0;
        } else if (!isClassExists(tblClass.getClass_name())) {
            return 2;
        }
        return 1;
    }

    public int removeClass(String className) {
        if (isClassExists(className)) {
            classRepository.removeClass(className);
            return 0;
        } else if(!isClassExists(className)){
            return 2;
        }
        return 1;
    }

    public List<String> getClasses() {
        return classRepository.getClasses();
    }
}
