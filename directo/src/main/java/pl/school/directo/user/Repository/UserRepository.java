package pl.school.directo.user.Repository;

import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.school.directo.user.Model.Tbl_user;

@Repository
@Table(name = "tbl_access")
public interface UserRepository extends JpaRepository<Tbl_user, Integer> {

}
