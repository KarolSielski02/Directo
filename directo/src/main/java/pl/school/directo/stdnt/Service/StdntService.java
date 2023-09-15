package pl.school.directo.stdnt.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.classN.Repository.ClassRepository;
import pl.school.directo.common.ResponseCode;
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

    public ResponseCode createNewStdnt(Tbl_stdnt tblStdnt) {
        boolean peselExists = isPeselExists(tblStdnt.getPesel());
        boolean classNameExists = classRepository.getClasses().contains(tblStdnt.getClassName());

        if (!peselExists && classNameExists){
            stdntRepository.createStdnt(tblStdnt.getPesel(), tblStdnt.getClassName());
            return ResponseCode.SUCCESS_CREATED;
        } else if (peselExists) {
            return ResponseCode.CONFLICT_ID_EXISTS;
        }
        return ResponseCode.FAILED_CREATION;
    }



    public boolean isPeselExists(String pesel){
        List<String> stringList = stdntRepository.getStdnts();
        for (String peselObj: stringList){
            if (Objects.equals(peselObj, pesel)){
                return true;
            }
        }
        return false;
    }

    public int modifyStdnt(Tbl_stdnt tblStdnt, String pesel) {
        boolean newPeselExists = isPeselExists(tblStdnt.getPesel());
        boolean currentPeselExists = isPeselExists(pesel);

        if (!Objects.equals(tblStdnt.getPesel(), pesel)) {
            if (currentPeselExists && !newPeselExists) {
                stdntRepository.modifyStdnt(tblStdnt.getPesel(), tblStdnt.getClassName(), pesel);
                return 0;
            } else  {
                return 2;
            }
        } else {
            if (currentPeselExists) {
                stdntRepository.modifyStdntNoPesel(pesel, tblStdnt.getClassName());
                return 0;
            } else{
                return 2;
            }
        }
    }

    public int removeStdnt(String pesel) {
        if (isPeselExists(pesel)) {
            stdntRepository.removeStdnt(pesel);
            return 0;
        } else if(!isPeselExists(pesel)){
            return 2;
        }
        return 1;
    }
}
