package pl.school.directo.stdnt.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tbl_stdnt {

    @Id
    @Column(name = "pesel")
    private String pesel; //max size 11
    @Column(name = "tbl_class_class_name")
    private String className;

    public Tbl_stdnt(String id, String className) {
        this.pesel = id;
        this.className = className;
    }

    public Tbl_stdnt() {
    }

    public String getPesel() {
        return pesel;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "Tbl_stdnt{" +
                "id=" + pesel +
                ", className='" + className + '\'' +
                '}';
    }
}
