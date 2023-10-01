package pl.school.directo.stdnt.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.classN.Repository.ClassRepository;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.stdnt.Model.Tbl_stdnt;
import pl.school.directo.stdnt.Repository.StdntRepository;

import java.util.List;
import java.util.Objects;


@Service
public class StdntService {

    private final StdntRepository stdntRepository;

    private final ClassRepository classRepository;

    public StdntService(StdntRepository stdntRepository, ClassRepository classRepository) {
        this.stdntRepository = stdntRepository;
        this.classRepository = classRepository;
    }

    public ResponseCodeEnums createNewStdnt(Tbl_stdnt tblStdnt) {
        boolean peselExists = isPeselExists(tblStdnt.getPesel());
        boolean classNameExists = classRepository.getClasses().contains(tblStdnt.getClassName());

        if (!peselExists && classNameExists && (tblStdnt.getPesel().length() < 12)){
            stdntRepository.createStdnt(tblStdnt.getPesel(), tblStdnt.getClassName());
            return ResponseCodeEnums.SUCCESS_CREATED;
        } else if (peselExists) {
            return ResponseCodeEnums.CONFLICT_ID_EXISTS;
        }
        return ResponseCodeEnums.FAILED_CREATION;
    }



    public boolean isPeselExists(String pesel){
        List<String> stringList = stdntRepository.getStdnts();
        for (String peselObj: stringList){
            System.out.println("UwU");
            if (Objects.equals(peselObj, pesel)){
                return true;
            }
        }
        return false;
    }

    public ResponseCodeEnums modifyStdnt(Tbl_stdnt tblStdnt, String pesel) {
        boolean newPeselExists = isPeselExists(tblStdnt.getPesel());
        boolean currentPeselExists = isPeselExists(pesel);

        if (!Objects.equals(tblStdnt.getPesel(), pesel)) {
            if (currentPeselExists && !newPeselExists && (tblStdnt.getPesel().length() < 12)) {
                stdntRepository.modifyStdnt(tblStdnt.getPesel(), tblStdnt.getClassName(), pesel);
                return ResponseCodeEnums.SUCCESS_MODIFIED;
            } else  {
                return ResponseCodeEnums.FAILED_MODIFICATION;
            }
        } else {
            if (currentPeselExists) {
                stdntRepository.modifyStdntNoPesel(pesel, tblStdnt.getClassName());
                return ResponseCodeEnums.CONFLICT_ID_EXISTS_MODIFICATION;
            } else{
                return ResponseCodeEnums.FAILED_MODIFICATION;
            }
        }
    }

    public ResponseCodeEnums removeStdnt(String pesel) {
        boolean isPeselExists = isPeselExists(pesel);
        if (isPeselExists) {
            stdntRepository.removeStdnt(pesel);
            return ResponseCodeEnums.SUCCESS_REMOVED;
        } else {
            return ResponseCodeEnums.OBJ_NOT_FOUND;
        }
    }

    public List<String> getStdnts() {
        return stdntRepository.getStdnts();
    }

    public String getStdntByPesel(String pesel) {
        if (isPeselExists(pesel)) {
            return stdntRepository.getStdntByPesel(pesel);
        } else{
            return ResponseCodeEnums.OBJ_NOT_FOUND.name();
        }
    }

}
