package pl.school.directo.access.Service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import pl.school.directo.access.Model.Tbl_access;
import pl.school.directo.access.Repository.AccessRepository;

import java.util.List;
import java.util.Objects;

@Service
public class AccessService {

    private final AccessRepository accessRepository;

    public AccessService(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    public String getIds(){
        List<String> dbList = accessRepository.getIDs();
        return new Gson().toJson(dbList);
    }

    public String getAccessClassById(String id) {
        if (isIdExists(id)) {
            Tbl_access obj = accessRepository.getAccessClassByID(id);
            return new Gson().toJson(obj);
        }
        return "2";
    }



    public int createNewAccessClass (Tbl_access tblAccess){
        if (!isIdExists(tblAccess.getAccess_class())) {
            accessRepository.addAccessClass(tblAccess.getAccess_class(),
                    tblAccess.getCan_crud_users(),
                    tblAccess.getCan_add_students(),
                    tblAccess.getCan_grade_students(),
                    tblAccess.getCan_export_to_pdf(),
                    tblAccess.getCan_clear_grades(),
                    tblAccess.getCan_modify_access_class());
            return 0;
        }else if (isIdExists(tblAccess.getAccess_class())){
            return 2;
        }
        return 1;
    }

    public int modifyAccessClass(Tbl_access tblAccess, String id) {
        if (isIdExists(id)){
            accessRepository.modifyAccessClass(tblAccess.getAccess_class(),
                    tblAccess.getCan_crud_users(),
                    tblAccess.getCan_add_students(),
                    tblAccess.getCan_grade_students(),
                    tblAccess.getCan_export_to_pdf(),
                    tblAccess.getCan_clear_grades(),
                    tblAccess.getCan_modify_access_class(),
                    id);
            return 0;
        }else if (!isIdExists(id)){
            return 2;
        }
        return 1;
    }

    public int removeAccessClass(String id) {
        if (isIdExists(id)){
            accessRepository.removeAccessClass(id);
            return 0;
        }else if (!isIdExists(id)){
            return 2;
        }
        return 1;
    }

    public boolean isIdExists(String accessID){
        List<String> stringList = accessRepository.getIDs();
        for (String id: stringList) {
            if (Objects.equals(accessID, id)){
                return true;
            }
        }
        return false;
    }



}
