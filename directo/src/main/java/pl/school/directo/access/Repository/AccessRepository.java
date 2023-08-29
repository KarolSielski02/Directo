package pl.school.directo.access.Repository;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.school.directo.access.Model.Tbl_access;

import java.util.List;

@Repository
@Table(name = "tbl_access")
public interface AccessRepository extends JpaRepository<Tbl_access, String> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tbl_access (access_class, can_crud_users, can_add_students, can_grade_students, can_export_to_pdf, can_clear_grades, can_modify_access_class, native_class) " +
            "VALUES (?1, ?2, ?3, ?4,?5, ?6, ?7, 0)",
            nativeQuery = true)
    void addAccessClass(String access_class,
                        boolean can_crud_users,
                        boolean can_add_students,
                        boolean can_grade_students,
                        boolean can_export_to_pdf,
                        boolean can_clear_grades,
                        boolean can_modify_access_class);

    @Query(value = "SELECT access_class FROM tbl_access", nativeQuery = true)
    List<String> getIDs();

    @Query(value = "SELECT * FROM tbl_access WHERE access_class=?1", nativeQuery = true)
    Tbl_access getAccessClassByID(String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_access SET access_class = ?1, can_crud_users = ?2, can_add_students = ?3, can_grade_students = ?4, can_export_to_pdf = ?5, can_clear_grades = ?6, can_modify_access_class = ?7 WHERE access_class = ?8", nativeQuery = true)
    void modifyAccessClassId(String accessClass, boolean canCrudUsers, boolean canAddStudents, boolean canGradeStudents, boolean canExportToPdf, boolean canClearGrades, boolean canModifyAccessClass, String id);

    @Modifying
    @Query(value = "UPDATE tbl_access SET can_crud_users = ?1, can_add_students = ?2, can_grade_students = ?3, can_export_to_pdf = ?4, can_clear_grades = ?5, can_modify_access_class = ?6 WHERE access_class = ?8", nativeQuery = true)
    void modifyAccessClassNoId(boolean canCrudUsers, boolean canAddStudents, boolean canGradeStudents, boolean canExportToPdf, boolean canClearGrades, Boolean canModifyAccessClass, String id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tbl_access WHERE access_class = ?1", nativeQuery = true)
    void removeAccessClass(String id);

}
