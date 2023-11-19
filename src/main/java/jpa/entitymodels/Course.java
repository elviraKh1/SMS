package jpa.entitymodels;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int cId;

    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<StudentCourses> studentCourses;

    @Column(name = "name")
    private String cName;

    @Column(name = "instructor")
    private String cInstructorName;

    public Course() {
    }

    public Course(int cId, String cName, String cInstructorName) {
        this.cId = cId;
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcInstructorName() {
        return cInstructorName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }

    public List<StudentCourses> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourses> studentCourses) {
        this.studentCourses = studentCourses;
    }

    @Override
    public String toString() {
        return "\nCourse{" +
                "id=" + cId +
                ", name='" + cName + '\'' +
                ", instructor='" + cInstructorName + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return  cId == course.cId
//                &&  Objects.equals(name, course.name)
//                &&  Objects.equals(instructor, course.instructor)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cId, cName, cInstructorName);
    }
}
