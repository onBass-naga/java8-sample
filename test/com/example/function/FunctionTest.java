package com.example.function;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by naga on 2014/03/23.
 */
public class FunctionTest {

    Function<String, String> enclose = new Function<String, String>() {
        @Override
        public String apply(String src) {
            return "<d<" + src + ">b>";
        }
    };

    @Test
    public void Functionをラムダで呼び出す() {
        List<String> cats = Arrays.asList("Tama", "Mike", "Kuro");

        cats.forEach( it -> System.out.println(enclose.apply(it)));
    }

    Function<String, String> upperCase = new Function<String, String>() {
        @Override
        public String apply(String src) {
            return src != null ? src.toUpperCase(): null;
        }
    };

    @Test
    public void andThenで関数を連結() {

        String actual = upperCase.andThen(enclose).apply("Kuro");
        assertThat(actual, is("<d<KURO>b>"));

        String actual2 = enclose.andThen(upperCase).apply("Tama");
        assertThat(actual2, is("<D<TAMA>B>"));
    }

    @Test
    public void 関数を合成して使う() {

        Function<String, String> upEnc = upperCase.andThen(enclose);
        String actual = upEnc.apply("Mike");
        assertThat(actual, is("<d<MIKE>b>"));
    }


    BiFunction<Integer, String, String> setDefault = new BiFunction<Integer, String, String>() {
        @Override
        public String apply(Integer key, String val) {
            return val == null? "default": val;
        }
    };

    @Test
    public void MapでFunctionを使う() {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "hoge");
        map.compute(1, (k, v) -> setDefault.apply(k, v));
        assertThat(map.get(1), is("hoge"));

        map.compute(2, (k, v) -> setDefault.apply(k, v));
        assertThat(map.get(2), is("default"));

    }
}
