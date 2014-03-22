package com.example;

/**
 * Created by naga on 2014/03/22.
 */
public class Anonymous {

    int age;
    Gender gender;

    public Anonymous(int age, Gender gender) {
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[")
                .append(age)
                .append(", ")
                .append(gender)
                .append("]")
                .toString();
    }
}
