package pl.school.directo.teacherToStudent.Service;

import org.springframework.stereotype.Service;
import pl.school.directo.common.Enums.ResponseCodeEnums;
import pl.school.directo.stdnt.Repository.StdntRepository;
import pl.school.directo.teacherToStudent.Model.Assoc_teacher_to_student;
import pl.school.directo.teacherToStudent.Repository.TeacherToStdntRepository;
import pl.school.directo.user.Repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
public class TeacherToStudentService {

    private final UserRepository userRepository;
    private final StdntRepository stdntRepository;
    private final TeacherToStdntRepository teacherToStdntRepository;

    public TeacherToStudentService(UserRepository userRepository, StdntRepository stdntRepository, TeacherToStdntRepository teacherToStdntRepository) {
        this.userRepository = userRepository;
        this.stdntRepository = stdntRepository;
        this.teacherToStdntRepository = teacherToStdntRepository;
    }
     protected boolean isStudentAndUserExists(String pesel, String login){
        boolean stdntExists = stdntRepository.getStdntByPesel(pesel).isEmpty();
        boolean teacherExists = userRepository.checkIfUserExistsOnlyLogin(login).isEmpty();
        return (stdntExists && teacherExists);
    }
    private boolean isTTSSimilarTTSExits(String studentsPesel, String teachersLogin) {
        return teacherToStdntRepository.checkIfSimilarTTSExists(studentsPesel, teachersLogin).isEmpty();
    }
    private boolean isIDExists(int id){
        List<Integer> integerList = teacherToStdntRepository.getIDs();
        for (Integer a: integerList){
            if (Objects.equals(id, a)){
                return true;
            }
        }
        return false;
    }
    public ResponseCodeEnums createTTSConnection(Assoc_teacher_to_student teacherToStudent) {
        boolean checkIfStdAndUserExist = isStudentAndUserExists(teacherToStudent.getStudentsPesel(), teacherToStudent.getTeachersLogin());
        boolean checkIfTTSConnectionExits = isTTSSimilarTTSExits(teacherToStudent.getStudentsPesel(), teacherToStudent.getTeachersLogin());

        String stdntPesel = teacherToStudent.getStudentsPesel();
        String userLogin = teacherToStudent.getTeachersLogin();

        if (checkIfStdAndUserExist && checkIfTTSConnectionExits){
            teacherToStdntRepository.createTTSConnection(stdntPesel, userLogin);
            return ResponseCodeEnums.SUCCESS_CREATED;
        } else if (!checkIfTTSConnectionExits){
            return ResponseCodeEnums.CONFLICT_ID_EXISTS;
        }
        return ResponseCodeEnums.FAILED_CREATION;
   }
//   I DONT THINK THAT MODIFICATION WILL BE NEEDED, WE'LL SEE IN FUTURE
//   public ResponseCodeEnums modifyTTS(Assoc_teacher_to_student teacherToStudent, int id, ){
//       boolean checkIfStdAndUserExist = checkIfStudentAndUserExists(teacherToStudent.getStudentsPesel(), teacherToStudent.getTeachersLogin());
//       boolean checkIfTTSConnectionExits = checkIfTTSSimilarTTSExits(teacherToStudent.getStudentsPesel(), teacherToStudent.getTeachersLogin());
//
//   }

    public ResponseCodeEnums removeTTSConnection (int id){
        if (isIDExists(id)){
            teacherToStdntRepository.removeTTSConnection(id);
            return ResponseCodeEnums.SUCCESS_CREATED;
        }else {
            return ResponseCodeEnums.FAILED_REMOVAL;
        }
    }

//TODO: Will need to add more methods but don't know which usage I need without doing UI

}
