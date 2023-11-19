package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class CourseService implements CourseDAO {

    @Override
    public List<Course> getAllCourses() {
        Session session = ServiceUtil.getSessionFactory().openSession();
        String hql = "From Course c ";
        TypedQuery<Course> query = session.createQuery(hql, Course.class);
        List<Course> result = query.getResultList();
        session.close();
        return result;
    }

    public Course getCourseById(Integer courseId) {
        Session session = ServiceUtil.getSessionFactory().openSession();
        String hql = "From Course c where c.cId = :courseId";
        TypedQuery<Course> query = session.createQuery(hql, Course.class);
        query.setParameter("courseId", courseId);
        try {
            Course course = query.getSingleResult();
            session.close();
            return course;
        } catch (NoResultException noResultException) {
            System.out.println(noResultException.getMessage());
            return null;
        }
    }
}
