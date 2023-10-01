package pl.school.directo.classN.Repository;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.school.directo.classN.Model.Tbl_class;

import java.util.List;

@Repository
@Table(name = "tbl_class")
public interface ClassRepository extends JpaRepository<Tbl_class, String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_class SET class_name = ?2 WHERE class_name = ?1", nativeQuery = true)
    void modifyClass(String className, String newName);

    @Query(value = "SELECT class_name FROM tbl_class", nativeQuery = true)
    List<String> getClasses();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tbl_class(class_name) VALUES (?1)", nativeQuery = true)
    void createClass(String className);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tbl_class WHERE class_name = ?1", nativeQuery = true)
    void removeClass(String className);

    @Query(value = "SELECT * FROM tbl_class WHERE class_name = ?1", nativeQuery = true)
    String getClassById(String id);
}
