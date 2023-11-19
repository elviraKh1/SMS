package jpa.entitymodels;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email")
    private String sEmail;

    @Column(name = "name")
    private String sName;

    @Column(name = "password")
    private String sPass;


    @OneToMany(mappedBy = "student",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<StudentCourses> studentCourses;

    public Student() {
    }

    public Student(String sEmail, String sName, String password, List studentCourses) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = password;
        this.studentCourses = studentCourses;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getPassword() {
        return sPass;
    }

    public void setPassword(String password) {
        this.sPass = password;
    }

    public List<StudentCourses> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourses> studentCourses) {
        this.studentCourses = studentCourses;
    }

    @Override
    public String toString() {
        return "\nStudent {" +
                "email='" + sEmail + '\'' +
                ", name='" + sName + '\'' +
                ", password='" + sPass + '\'' +
                "}";
    }
}
