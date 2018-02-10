package com.sdm.delta.serializable;

import java.io.Serializable;

public abstract class Person implements Serializable {
    String name;
    int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}
