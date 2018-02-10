package com.sdm.delta.serializable;

public class Student extends Person  {
    public static final long serialVersionUID = 1234L;

    private long studentId;

    public Student(long studentId, String name, int age) {
        super();
        this.studentId = studentId;
        super.name = name;
        super.age = age;

    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}