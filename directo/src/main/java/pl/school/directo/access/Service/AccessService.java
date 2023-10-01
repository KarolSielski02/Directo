package pl.school.directo.access.Service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import pl.school.directo.access.Model.Tbl_access;
import pl.school.directo.access.Repository.AccessRepository;
import pl.school.directo.common.Enums.ResponseCodeEnums;

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



    public ResponseCodeEnums createNewAccessClass (Tbl_access tblAccess){
        boolean isIdExists = isIdExists(tblAccess.getAccess_class());
        if (!isIdExists) {
            accessRepository.addAccessClass(tblAccess.getAccess_class(),
                    tblAccess.getCan_crud_users(),
                    tblAccess.getCan_add_students(),
                    tblAccess.getCan_grade_students(),
                    tblAccess.getCan_export_to_pdf(),
                    tblAccess.getCan_clear_grades(),
                    tblAccess.getCan_modify_access_class());
            return ResponseCodeEnums.SUCCESS_CREATED;
        }else {
            return ResponseCodeEnums.FAILED_CREATION;
        }
    }

    public ResponseCodeEnums modifyAccessClass(Tbl_access tblAccess, String id) {
        if (!Objects.equals(tblAccess.getAccess_class(), id)){
            if (isIdExists(id) && !isIdExists(tblAccess.getAccess_class())){
                accessRepository.modifyAccessClassId(tblAccess.getAccess_class(),
                        tblAccess.getCan_crud_users(),
                        tblAccess.getCan_add_students(),
                        tblAccess.getCan_grade_students(),
                        tblAccess.getCan_export_to_pdf(),
                        tblAccess.getCan_clear_grades(),
                        tblAccess.getCan_modify_access_class(),
                        id);
                return ResponseCodeEnums.SUCCESS_MODIFIED;
            }else if (!isIdExists(id)){
                return ResponseCodeEnums.FAILED_MODIFICATION;
            }
        }else {
            if (isIdExists(id)){
                accessRepository.modifyAccessClassNoId(
                        tblAccess.getCan_crud_users(),
                        tblAccess.getCan_add_students(),
                        tblAccess.getCan_grade_students(),
                        tblAccess.getCan_export_to_pdf(),
                        tblAccess.getCan_clear_grades(),
                        tblAccess.getCan_modify_access_class(),
                        id);
                return ResponseCodeEnums.SUCCESS_MODIFIED;
            }else if (!isIdExists(id)){
                return ResponseCodeEnums.FAILED_MODIFICATION;
            }
        }
        return ResponseCodeEnums.METHOD_ERROR;
    }

    public ResponseCodeEnums removeAccessClass(String id) {
        boolean isIdExists = isIdExists(id);
        if (isIdExists){
            accessRepository.removeAccessClass(id);
            return ResponseCodeEnums.SUCCESS_REMOVED;
        }else {
            return ResponseCodeEnums.FAILED_REMOVAL;
        }
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
