package com.example.optional;

import com.example.BloodType;
import com.example.Gender;
import com.example.Person;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by naga on 2014/03/21.
 */
public class OptionalTest {

    Person person = null;

    @Before
    public void init() {
        person = new Person("Jhone", 23, Gender.MALE, BloodType.A);
    }

    @Test
    public void jobが未設定の場合() {

        Optional<String> job = person.getJob();

        assertThat(job.isPresent(), is(false));
        assertThat(job.orElse("自宅警備員"), is("自宅警備員"));

    }

    @Test
    public void jobが設定されている場合() {

        person.setJob("singer");
        Optional<String> job = person.getJob();

        assertThat(job.isPresent(), is(true));
        assertThat(job.orElse("自宅警備員"), is("自宅警備員"));

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void jobが未設定の場合は例外を投げる() {

        exception.expect(IllegalStateException.class);
        exception.expectMessage("働いたら負けだと思ってる");

        Optional<String> job = person.getJob();
        job.orElseThrow(() -> new IllegalStateException("働いたら負けだと思ってる"));
    }
}
