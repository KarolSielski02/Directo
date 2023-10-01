package pl.school.directo.user.Model;
import jakarta.persistence.*;

@Entity
public class Tbl_user {

    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "tbl_access_access_class")
    private String tbl_access_access_class;
    @Column(name = "unsuc_logins")
    private int unsuc_logins;

    @Column(name = "blocked")
    private Boolean blocked;

    public Tbl_user(String login, String password, String tbl_access_access_class, int unsuc_logins, Boolean blocked) {
        this.login = login;
        this.password = password;
        this.tbl_access_access_class = tbl_access_access_class;
        this.unsuc_logins = unsuc_logins;
        this.blocked = blocked;
    }

    public Tbl_user() {
    }
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getTbl_access_access_class() {
        return tbl_access_access_class;
    }

    public int getUnsuc_logins() {
        return unsuc_logins;
    }

    public Boolean isBlocked() {
        return blocked;
    }

    @Override
    public String toString() {
        return "Tbl_user{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", tbl_access_access_class='" + tbl_access_access_class + '\'' +
                ", unsuc_logins=" + unsuc_logins +
                ", blocked=" + blocked +
                '}';
    }
}
