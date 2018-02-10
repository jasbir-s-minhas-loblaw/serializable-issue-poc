package com.sdm.delta.serializable;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class CourseTest {

    private Course inCourse;
    private Course outCourse;
    @Before
    public void setUp() {
        inCourse = new Course("101-CompSci");
        for (int i = 101; i <= 102; i++) {
            inCourse.addStudent(new Student(i, getStudentName(i), 16));
        }
        System.out.println(inCourse);
        String filePath = "course.ser";
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(inCourse);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @Test
    public void testDeserializes() {
        String filePath = "course.ser";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            outCourse = (Course) inputStream.readObject();
            System.out.println(outCourse);

        } catch (ClassNotFoundException ex) {
            System.err.println("Class not found: " + ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("IO error: " + ex);
            ex.printStackTrace();
        }
//        for (int i = 101; i <= 105; i++) {
//            inCourse.addStudent(new Student(i, getStudentName(i), 16));
//        }
    }

    private String getStudentName(int studentId) {
        return "StudentName - " + studentId;
    }
}
