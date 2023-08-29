package pl.school.directo.stdnt.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.classN.Repository.ClassRepository;
import pl.school.directo.stdnt.Model.Tbl_stdnt;
import pl.school.directo.stdnt.Repository.StdntRepository;

import java.util.List;


@Service
public class StdntService {

    private final StdntRepository stdntRepository;

    private final ClassRepository classRepository;

    public StdntService(StdntRepository stdntRepository, ClassRepository classRepository) {
        this.stdntRepository = stdntRepository;
        this.classRepository = classRepository;
    }

    public int createNewStdnt(Tbl_stdnt tblStdnt) {
        if (!isPeselExists(tblStdnt.getPesel()) && classRepository.getClasses().contains(tblStdnt.getClassName())){
            stdntRepository.createStdnt(tblStdnt.getPesel(), tblStdnt.getClassName());
            return 0;
        } else if (isPeselExists(tblStdnt.getPesel())) {
            return 2;
        }
        return 1;
    }



    public boolean isPeselExists(int pesel){
        List<Integer> integerList = stdntRepository.getStdnts();
        for (int peselObj: integerList){
            if (peselObj == pesel){
                return true;
            }
        }
        return false;
    }

    public int modifyStdnt(Tbl_stdnt tblStdnt, int pesel) {
        boolean newPeselExists = isPeselExists(tblStdnt.getPesel());
        boolean currentPeselExists = isPeselExists(pesel);

        if (tblStdnt.getPesel() != pesel) {
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
}
