package pl.school.directo.access.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tbl_access {

    @Id
    @Column(name = "access_class")
    private String access_class;
    @Column(name = "can_crud_users")
    private Boolean can_crud_users;
    @Column(name = "can_add_students")
    private Boolean can_add_students;
    @Column(name = "can_grade_students")
    private Boolean can_grade_students;
    @Column(name = "can_export_to_pdf")
    private Boolean can_export_to_pdf;
    @Column(name = "can_clear_grades")
    private Boolean can_clear_grades;
    @Column(name = "can_modify_access_class")
    private Boolean can_modify_access_class;

    public Tbl_access(String access_class, Boolean can_crud_users, Boolean can_add_students, Boolean can_grade_students, Boolean can_export_to_pdf, Boolean can_clear_grades, Boolean can_modify_access_class) {
        this.access_class = access_class;
        this.can_crud_users = can_crud_users;
        this.can_add_students = can_add_students;
        this.can_grade_students = can_grade_students;
        this.can_export_to_pdf = can_export_to_pdf;
        this.can_clear_grades = can_clear_grades;
        this.can_modify_access_class = can_modify_access_class;
    }

    public Tbl_access() {
    }

    public String getAccess_class() {
        return access_class;
    }

    public boolean getCan_crud_users() {
        return can_crud_users;
    }

    public boolean getCan_add_students() {
        return can_add_students;
    }

    public boolean getCan_grade_students() {
        return can_grade_students;
    }

    public boolean getCan_export_to_pdf() {
        return can_export_to_pdf;
    }

    public boolean getCan_clear_grades() {
        return can_clear_grades;
    }

    public Boolean getCan_modify_access_class() {
        return can_modify_access_class;
    }


    @Override
    public String toString() {
        return "Tbl_access{" +
                "access_class='" + access_class + '\'' +
                ", can_crud_users=" + can_crud_users +
                ", can_add_students=" + can_add_students +
                ", can_grade_students=" + can_grade_students +
                ", can_export_to_pdf=" + can_export_to_pdf +
                ", can_clear_grades=" + can_clear_grades +
                ", can_modify_access_class=" + can_modify_access_class +
                '}';
    }
}
