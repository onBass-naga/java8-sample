package com.example;

import java.util.Optional;

/**
 * Created by naga on 2014/03/21.
 */
public class Person {

    public String name;
    public int age;
    public Gender gender;
    public BloodType bloodType;
    private String job;

    public Person(String name, int age, Gender gender, BloodType bloodType) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Optional<String> getJob() {
        return Optional.ofNullable(job);
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(name)
                .append(", ")
                .append(age)
                .append(", ")
                .append(gender)
                .append(", ")
                .append(bloodType)
                .toString();
    }
}
