package com.sdm.delta.serializable;

import java.io.BufferedReader;

public class Student extends Person {
    public static final long serialVersionUID = 1234L;

    private long studentId;

    public Student(long studentId, String name, int age) {
        super();
        System.exit(1);
        this.studentId = studentId;
        super.name = name;
        super.age = age;

    }

    @Override
    public String toString() {
        System.exit(1);
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}