package pl.school.directo.teacherToStudent.Repository;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.school.directo.teacherToStudent.Model.Assoc_teacher_to_student;

import java.util.List;

@Repository
@Table(name = "assoc_teacher_to_student")
public interface TeacherToStdntRepository extends JpaRepository<Assoc_teacher_to_student, Integer> {
    @Query(value = "SELECT * FROM assoc_teacher_to_student WHERE tbl_stdnt_pesel = ?1 AND tbl_user_login = ?2", nativeQuery = true)
    String checkIfSimilarTTSExists(String studentsPesel, String teachersLogin);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO assoc_teacher_to_student(tbl_stdnt_pesel, tbl_user_login)" +
            "VALUES (?1, ?2)"
            ,nativeQuery = true)
    void createTTSConnection(String stdntPesel, String userLogin);

    @Query(value = "SELECT id FROM assoc_teacher_to_student", nativeQuery = true)
    List<Integer> getIDs();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM assoc_teacher_to_student WHERE tbl_user_login = ?1", nativeQuery = true)
    void removeTTSConnection(int id);
}
