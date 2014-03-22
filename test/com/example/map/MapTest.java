package com.example.map;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by naga on 2014/03/22.
 */
public class MapTest {

    public Map<Integer, String> map = new HashMap<>();

    @Before
    public void init() {
        map.clear();
        map.put(1, "hoge");
        map.put(2, "fuga");
    }

    @Test
    public void forEachを使ってみる() {
        map.forEach((key, val) -> System.out.println(key + "の値は" + val));
    }

    private <K, V> void dump(Map<K, V> map) {
        map.forEach((key, val) -> System.out.println(key + "の値は" + val));
    }

    @Test
    public void 要素が存在する場合のみ処理を行う() {
        map.computeIfPresent(2, (key, val) -> "[" + val + key + "]" );
        assertThat(map.get(2), is("[fuga2]"));

        // ぬるぽが発生しない。
        map.computeIfPresent(7, (key, val) -> "[" + val + key + "]");

        dump(map);
    }

    @Test
    public void 要素が存在する場合のみ処理を行う_削除する() {
        map.computeIfPresent(2, (key, val) -> null );
        assertThat(map.size(), is(1));

        map.computeIfPresent(1, (key, val) -> map.remove(key));
        assertThat(map.size(), is(0));

        dump(map);
    }

    @Test
    public void 特定の処理を行う() {

        map.compute(1, (k, v) -> (v != null) ? "ある" : "ない");
        assertThat(map.get(1), is("ある"));

        map.put(3, null);
        map.compute(3, (k, v) -> (v != null) ? "ある" : "ない");
        assertThat(map.get(3), is("ない"));

        map.compute(7, (k, v) -> (v != null) ? "ある" : "ない");
        assertThat(map.get(7), is("ない"));

        dump(map);
    }

    @Test
    public void Keyが存在しない時のみ要素を追加する() {

        map.putIfAbsent(3, "ugya");
        assertThat(map.size(), is(3));

        map.putIfAbsent(3, "ugyaaaa!!");
        assertThat(map.size(), is(3));
        assertThat(map.get(3), is("ugya"));

        dump(map);
    }

    @Test
    public void KeyとValueが指定した値と一致した場合のみ削除する() {

        map.remove(1, "hogee");
        assertThat(map.get(1), is("hoge"));
        assertThat(map.size(), is(2));

        dump(map);

        map.remove(1, "hoge");
        assertThat(map.get(1), is(nullValue()));
        assertThat(map.size(), is(1));

        dump(map);
    }

    @Test
    public void 要素が存在しなかった場合にデフォルト値を返却する() {

        String storedVal = map.getOrDefault(1, "not-exists");
        assertThat(map.get(1), is(storedVal));

        String defaultVal = map.getOrDefault(7, "not-exists");
        assertThat(defaultVal, is("not-exists"));
    }

    @Test
    public void 要素が存在して指定した値と一致した場合に任意の値と置き換える() {

        map.replace(1, "hogee", "Hyyahaaah!!");
        assertThat(map.get(1), is("hoge"));

        map.replace(1, "hoge", "Hyyahaaah!!");
        assertThat(map.get(1), is("Hyyahaaah!!"));

        map.replace(7, "hoge", "Hyyahaaah!!");
        dump(map);
    }

    @Test
    public void 要素が存在した場合に任意の値と置き換える() {

        map.replace(1, "Hyyahaaah!!");
        assertThat(map.get(1), is("Hyyahaaah!!"));

        map.replace(7, "Hyyahaaah!!");
        dump(map);
    }

    @Test
    public void 指定した値をマージする() {

        map.merge(1, "!?", (value, newValue) -> value.concat(newValue));
        assertThat(map.get(1), is("hoge!?"));

        map.put(3, null);
        map.merge(3, "!?", (value, newValue) -> value.concat(newValue));
        assertThat(map.get(3), is("!?"));

        map.merge(7, "!?", (value, newValue) -> value.concat(newValue));
        assertThat(map.get(7), is("!?"));

        dump(map);
    }
}
