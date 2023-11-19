package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {

    @Override
    public List<Student> getAllStudents() {
        Session session = ServiceUtil.getSessionFactory().openSession();
        String hql = "From Student s ";
        TypedQuery<Student> query = session.createQuery(hql, Student.class);
        List<Student> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Student getStudentByEmail(String email) {
        Session session = ServiceUtil.getSessionFactory().openSession();
        String hql = "From Student s where s.sEmail = :email";
        TypedQuery<Student> query = session.createQuery(hql, Student.class);
        query.setParameter("email", email);
        try {
            Student result = query.getSingleResult();
            session.close();
            return result;
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Session session = ServiceUtil.getSessionFactory().openSession();
        String hql = "From Student s where s.sEmail = :email and password = :password ";
        TypedQuery<Student> query = session.createQuery(hql, Student.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            Student result = query.getSingleResult();
            session.close();
            return result != null;
        } catch (NoResultException nre) {
            return false;
        }
    }

    @Override
    public void registerStudentToCourse(Student student, int courseId) {
        Session session = ServiceUtil.getSessionFactory().openSession();
        org.hibernate.Transaction t = session.beginTransaction();

        CourseService courseService = new CourseService();
        Course course = courseService.getCourseById(courseId);

        StudentCourses studentCourses = new StudentCourses();
        studentCourses.setCourse(course);
        studentCourses.setStudent(student);
        session.persist(studentCourses);

        t.commit();
        session.close();
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Session session = ServiceUtil.getSessionFactory().openSession();
        String hql = "From StudentCourses where student.sEmail = :email  ";
        TypedQuery<StudentCourses> query = session.createQuery(hql, StudentCourses.class);
        query.setParameter("email", email);
        List<StudentCourses> student = query.getResultList();
        List<Course> courses = new ArrayList<>();

        for (StudentCourses studentCourse : student) {
            courses.add(studentCourse.getCourse());
        }
        session.close();
        return courses;
    }
}