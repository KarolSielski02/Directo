package pl.school.directo.teacherToStudent.Model;

import jakarta.persistence.*;

@Entity
public class Assoc_teacher_to_student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "tbl_stdnt_pesel")
    private String studentsPesel;

    @Column(name = "tbl_user_login")
    private String TeachersLogin;

    public Assoc_teacher_to_student(int id, String studentsPesel, String teachersID) {
        this.id = id;
        this.studentsPesel = studentsPesel;
        this.TeachersLogin = teachersID;
    }

    public Assoc_teacher_to_student(){
    }

    public int getId() {
        return id;
    }

    public String getStudentsPesel() {
        return studentsPesel;
    }

    public String getTeachersLogin() {
        return TeachersLogin;
    }

    @Override
    public String toString() {
        return "Assoc_teacher_to_student{" +
                "id=" + id +
                ", studentsPesel='" + studentsPesel + '\'' +
                ", TeachersID='" + TeachersLogin + '\'' +
                '}';
    }
}
