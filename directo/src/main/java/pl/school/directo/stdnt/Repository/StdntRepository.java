package pl.school.directo.stdnt.Repository;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.school.directo.stdnt.Model.Tbl_stdnt;

import java.util.List;

@Repository
@Table(name = "tbl_stdnt")
public interface StdntRepository extends JpaRepository<Tbl_stdnt, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tbl_stdnt (pesel, tbl_class_class_name) VALUES (?1, ?2)", nativeQuery = true)
    void createStdnt(int id, String className);

    @Query(value = "SELECT pesel FROM tbl_stdnt", nativeQuery = true)
    List<Integer> getStdnts();
}
