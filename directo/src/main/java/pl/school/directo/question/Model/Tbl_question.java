package pl.school.directo.question.Model;

import jakarta.persistence.*;

@Entity
public class Tbl_question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "category")
    private int category;
    @Column(name = "question")
    private int question;
    @Column(name = "desc")
    private String desc;

    public Tbl_question() {
    }

    public Tbl_question(int id, int category, int question, String desc) {
        this.id = id;
        this.category = category;
        this.question = question;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public int getQuestion() {
        return question;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Tbl_question{" +
                "id=" + id +
                ", category=" + category +
                ", question=" + question +
                ", desc='" + desc + '\'' +
                '}';
    }
}
