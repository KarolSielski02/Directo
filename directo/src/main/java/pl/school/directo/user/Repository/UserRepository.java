package pl.school.directo.user.Repository;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.school.directo.user.Model.Tbl_user;

import java.util.List;

@Repository
@Table(name = "tbl_user")
public interface UserRepository extends JpaRepository<Tbl_user, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tbl_user (login,password,tbl_access_access_class, unsuc_logins)" +
            "VALUES (?1, ?2, ?3, 0)"
            ,nativeQuery = true)
    void createUser(String login,
                    String passwordH,
                    String tbl_access_access_class);

    @Query(value = "SELECT login FROM tbl_user", nativeQuery = true)
    List<String> getLogins();

    @Query(value = "SELECT password FROM tbl_user WHERE login = ?1", nativeQuery = true)
    String getPW(String login);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_user SET login =?1, tbl_access_access_class=?2 where login =?3", nativeQuery = true)
    void modifyUserLogin(String newLogin, String tbl_access_access_class, String oldLogin);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_user SET tbl_access_access_class=?2 where login =?1", nativeQuery = true)
    void modifyUserNoLogin(String Login, String tbl_access_access_class);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_user SET password = ?2 where login =?1", nativeQuery = true)
    void changePW(String login, String password);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tbl_user WHERE login = ?1", nativeQuery = true)
    void removeUser(String login);

    @Query(value = "SELECT unsuc_logins FROM tbl_user WHERE login = ?1", nativeQuery = true)
    Integer getUnsucLogs(String login);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_user SET unsuc_logins = ?2 WHERE login = ?1", nativeQuery = true)
    void increaseUnsucLogs(String login, int num);

    @Query(value = "SELECT blocked FROM tbl_user WHERE login = ?1", nativeQuery = true)
    Boolean getIsBlocked(String login);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_user SET blocked = 1 WHERE login = ?1", nativeQuery = true)
    void getBlocked(String login);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_user SET blocked = 0, unsuc_logins = 0 WHERE login = ?1", nativeQuery = true)
    void getUnBlocked(String login);

    @Query(value = "SELECT * FROM Tbl_user where login = ?1", nativeQuery = true)
    Tbl_user getUser(String login);
}
