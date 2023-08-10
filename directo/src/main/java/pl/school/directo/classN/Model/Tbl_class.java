package pl.school.directo.classN.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Tbl_class {

    @Id
    @Column(name = "class_name")
    private String class_name;

    public Tbl_class(String class_name) {
        this.class_name = class_name;
    }

    public Tbl_class() {
    }

    public String getClass_name() {
        return class_name;
    }

    @Override
    public String toString() {
        return "Tbl_class{" +
                "class_name='" + class_name + '\'' +
                '}';
    }
}
