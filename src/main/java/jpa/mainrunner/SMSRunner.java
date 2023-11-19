package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.exceptions.MenuInputMismatchException;
import jpa.service.CourseService;
import jpa.service.StudentService;
import jpa.util.Util;

import java.util.*;

public final class SMSRunner {

    private final Scanner scanner;
    private final CourseService courseService;
    private final StudentService studentService;
    private Student currentStudent;
    private StringBuilder sb;
    private Set<Integer> menuSet;

    private SMSRunner() {
        scanner = new Scanner(System.in);
        courseService = new CourseService();
        studentService = new StudentService();
        sb = new StringBuilder();
        menuSet = new HashSet<>();
    }

    public static void main(String[] args) {
        SMSRunner sms = new SMSRunner();
        sms.runMainMenu();
    }

    private void runMainMenu() {
        System.out.println("Welcome to School Management System");
        int menuChoice = displayWelcomeMenu();
        switch (menuChoice) {
            case 1:
                authentication();
                break;
            case 2:
                Util.exitMessageAndQuit();
                break;
        }
    }

    private int displayWelcomeMenu() {
////////////////   set menu staff
        menuSet.clear();
        menuSet.add(1);
        menuSet.add(2);
        sb.delete(0, sb.length());
        sb.append("\n-----------------  Main menu -------------" +
                "\n1.Student Login" +
                "\n2.Quit Application" +
                "\nPlease enter selection 1 or 2: ");
////////////////

        int menu = menuNumberHandler(menuSet, sb);
        return menu;
    }

    private void authentication() {
        System.out.print("\nEnter your email address: ");
        String email = scanner.next();
        System.out.print("\nEnter your password: ");
        String password = scanner.next();

        currentStudent = studentService.getStudentByEmail(email);
        if (currentStudent == null) {
            System.out.println("\nStudent with " + email + " not registered in system");
            Util.exitMessageAndQuit();
        }

        if (studentService.validateStudent(email, password)) {
            runStudentMenu();
        } else {
            System.out.println("\nWrong Credentials. Please try again.");
            Util.exitMessageAndQuit();
        }
    }

    private void runStudentMenu() {
////////////////    set menu staff
        menuSet.clear();
        menuSet.add(1);
        menuSet.add(2);
        menuSet.add(3);
        menuSet.add(4);
        sb.delete(0, sb.length());
        sb.append("\n-----------------  Student menu -------------" +
                "\n1. Display all courses" +
                "\n2. Display my courses" +
                "\n3. Register a courses" +
                "\n4. Exit" +
                "\nPlease Enter Selection: ");
        int menu = menuNumberHandler(menuSet, sb);
////////////////

        switch (menu) {
            case 1 -> printAllCourses();
            case 2 -> runStudentCourses();
            case 3 -> runStudentRegistration();
            case 4 -> Util.exitMessageAndQuit();
        }
    }

    private void printAllCourses() {
        System.out.println("\n-----------------  All courses -------------");
        List<Course> courses = courseService.getAllCourses();
        printFormattedCourses(courses);

        runStudentMenu();
    }

    private void runStudentCourses() {
        List<Course> courses = studentService.getStudentCourses(currentStudent.getsEmail());
        System.out.println("\n----------------- My courses -------------");
        printFormattedCourses(courses);

        runStudentMenu();
    }

    private void runStudentRegistration() {
        List<Course> allCourses = courseService.getAllCourses();
        List<Course> allCoursesForMenu = new ArrayList<>(allCourses);
        List<Course> studentCoursesCurrent = studentService.getStudentCourses(currentStudent.getsEmail());
        allCourses.removeAll(studentCoursesCurrent);

        System.out.println("\n----------------- Courses for registration -------------");
        printFormattedCourses(allCourses);
        System.out.print("Enter Course Number: ");

///////////    set menu staff
        menuSet.clear();
        allCoursesForMenu.forEach(course -> menuSet.add(course.getcId()));  //read all course id for handling in menuNumberHandler
        sb.delete(0, sb.length());
        int number = menuNumberHandler(menuSet, sb);
///////////

        boolean isCourseRegistered = studentCoursesCurrent.stream().anyMatch(course -> course.getcId() == number);
        if (isCourseRegistered) {
            System.out.println(Util.ANSI_RED + "You are already registered in that course! Please try again." + Util.RESET);

            runStudentMenu();
        }

        studentService.registerStudentToCourse(currentStudent, number);
        List<Course> coursesNew = studentService.getStudentCourses(currentStudent.getsEmail());
        System.out.println("\n----------------- My courses (updated) -------------");
        printFormattedCourses(coursesNew);

        runStudentMenu();
    }

    private void printFormattedCourses(List<Course> courses) {
        System.out.printf(" # | %30s | %s\n", "Course name", "Instructor");
        for (Course course : courses) {
            System.out.printf("%2d | %30s | %s\n", course.getcId(), course.getcName(), course.getcInstructorName());
        }
    }

    public int menuNumberHandler(Set menuNum, StringBuilder sb) {
        Integer menu=null;
        while (true) {
            try {
                System.out.print(sb.toString());
                menu = scanner.nextInt();
                if (menuNum.contains(menu)) {
                    break;
                } else
                    throw new MenuInputMismatchException(" wrong character");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println(Util.ANSI_RED + "wrong character. Please try again." + Util.RESET);
                scanner.nextLine();
            }
        }
        return menu;
    }

}