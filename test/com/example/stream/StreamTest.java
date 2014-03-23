package com.example.stream;

import com.example.Anonymous;
import com.example.BloodType;
import com.example.Gender;
import com.example.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Created by naga on 2014/03/21.
 */
public class StreamTest {

    static List<Person> persons = null;

    @BeforeClass
    public static void init() {
        persons = Arrays.asList(
                new Person("John", 23, Gender.MALE, BloodType.A)
                , new Person("Ai", 17, Gender.FEMALE, BloodType.O)
                , new Person("Vin", 26, Gender.MALE, BloodType.AB)
                , new Person("Jack", 33, Gender.MALE, BloodType.B)
                , new Person("Steve", 19, Gender.MALE, BloodType.A)
                , new Person("Jessica", 24, Gender.FEMALE, BloodType.O)
                , new Person("Julia", 30, Gender.FEMALE, BloodType.B)
        );
    }

    @Test
    public void とりあえずリストの中身を表示() {

        persons.stream()
                .forEach(
                    System.out::println
                );
    }

    @Test
    public void 年齢順に表示() {

        ToIntFunction<Person> toAge = Person::getAge;

        persons.stream()
                .sorted(Comparator.comparingInt(toAge).reversed())
                .forEach(
                    System.out::println
                );
    }

    @Test
    public void 名前だけを列挙する() {

        persons.stream()
                .map(Person::getName)
                .forEach(
                    System.out::println
                );
    }

    @Test
    public void _20代の人だけ表示() {

        persons.stream()
                .filter(it -> it.age > 19 && it.age < 30)
                .forEach(
                    System.out::println
                );
    }

    @Test
    public void 別のオブジェクトに詰め替え() {

        List<Anonymous> anonymouses = persons.stream()
                .map(it -> new Anonymous(it.age, it.gender))
                .collect(Collectors.toList());

        System.out.println(anonymouses);
    }

    @Test
    public void 年代別に人数を集計() {

        Map<String, List<Person>> grouped = persons.stream()
                .collect(Collectors.groupingBy(
                        person -> (person.age / 10) * 10 + "代"));

        System.out.println(grouped);


        Map<String, Integer> counted = grouped.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), entry -> entry.getValue().size()));

        System.out.println(new TreeMap<>(counted));

    }

    @Test
    public void 年代別に人数を集計2() {

        Map<String, Integer> counted = persons.stream()
                .collect(Collectors.groupingBy(
                        person -> (person.age / 10) * 10 + "代"))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), entry -> entry.getValue().size()));

        System.out.println(new TreeMap<>(counted));
    }

}
