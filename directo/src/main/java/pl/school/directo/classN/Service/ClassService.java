package pl.school.directo.classN.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.classN.Model.Tbl_class;
import pl.school.directo.classN.Repository.ClassRepository;
import pl.school.directo.common.Enums.ResponseCodeEnums;

import java.util.List;
import java.util.Objects;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }


    public ResponseCodeEnums createNewClass(Tbl_class tblClass) {
        if (!isClassExists(tblClass.getClass_name())){
            classRepository.createClass(tblClass.getClass_name());
            return ResponseCodeEnums.SUCCESS_CREATED;
        } else if (isClassExists(tblClass.getClass_name())) {
            return ResponseCodeEnums.CONFLICT_ID_EXISTS;
        }
        return ResponseCodeEnums.FAILED_CREATION;
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

    public ResponseCodeEnums modifyClass(Tbl_class tblClass, String newName) {
        if (isClassExists(tblClass.getClass_name()) && !isClassExists(newName)){
            classRepository.modifyClass(tblClass.getClass_name(), newName);
            return ResponseCodeEnums.SUCCESS_MODIFIED;
        } else if (!isClassExists(tblClass.getClass_name())) {
            return ResponseCodeEnums.OBJ_NOT_FOUND;
        }
        return ResponseCodeEnums.FAILED_MODIFICATION;
    }

    public ResponseCodeEnums removeClass(String className) {
        if (isClassExists(className)) {
            classRepository.removeClass(className);
            return ResponseCodeEnums.SUCCESS_REMOVED;
        } else if(!isClassExists(className)){
            return ResponseCodeEnums.OBJ_NOT_FOUND;
        }
        return ResponseCodeEnums.FAILED_REMOVAL;
    }

    public List<String> getClasses() {
        return classRepository.getClasses();
    }

    public String getClassById(String id) {
        if (isClassExists(id)) {
            return classRepository.getClassById(id);
        } else{
            return ResponseCodeEnums.OBJ_NOT_FOUND.name();
        }
    }
}
