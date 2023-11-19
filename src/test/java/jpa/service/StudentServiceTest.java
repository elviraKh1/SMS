package jpa.service;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;


public class StudentServiceTest {
    private static StudentService studentService;

    @BeforeAll
    public static void initService() {
        studentService = new StudentService();
    }

    @ParameterizedTest
    @CsvSource({"asdf1@asd.sdf", "1111", "select from"})
    public void getStudentByWrongEmailTest(String email) {
        Student actualStudent = studentService.getStudentByEmail(email);

        Assertions.assertNull(actualStudent);
    }

    @ParameterizedTest
    @CsvSource({"asdf", "cjaulme9@bing.com", "aiannitti7@is.gd"})
    public void getStudentRightByEmailTest(String email) {
        Student actualStudent = studentService.getStudentByEmail(email);

        Assertions.assertEquals(email, actualStudent.getsEmail());
    }

    @ParameterizedTest
    @CsvSource({"asdf,asdf", "cjaulme9@bing.com,FnVklVgC6r6", "aiannitti7@is.gd,TWP4hf5j"})
    public void validateStudentTest(String email, String password) {
        boolean isExistStudent = studentService.validateStudent(email, password);

        Assertions.assertTrue(isExistStudent);
    }

    @ParameterizedTest
    @CsvSource({"asdf,1asdf", "^aiannitti7@is.gd,", ","})
    public void invalidateStudentTest(String email, String password) {
        boolean isExistStudent = studentService.validateStudent(email, password);

        Assertions.assertFalse(isExistStudent);
    }


    @Test
    public void isClassStudentByEmailTest() {
        Object actualStudent = studentService.getStudentByEmail("asdf");

        Assertions.assertInstanceOf(Student.class, actualStudent);
    }


    @AfterAll
    public static void closeService() {
        ServiceUtil.closeSessionFactory();
    }
}
