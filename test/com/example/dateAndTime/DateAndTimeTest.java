package com.example.dateAndTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by naga on 2014/03/22.
 */
public class DateAndTimeTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {

    }

    @Test
    public void LocalDate_日付文字列をパースする() {
        LocalDate date = LocalDate.parse("2007/12/03", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        System.out.print(date);
    }

    @Test
    public void LocalDate_日付文字列をパースする_存在しない日付は適当に解釈される() {
        LocalDate date = LocalDate.parse("2007/09/31", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        System.out.print(date);
    }

    @Test
    public void LocalDateTime_日付文字列をパースする_時間が範囲外だと例外となる() {

        exception.expect(DateTimeParseException.class);

        LocalDateTime date = LocalDateTime.parse("2007/09/30 24:00:02", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        System.out.print(date);
    }

    @Test
    public void LocalDateTime_日付文字列をパースする_存在しない日時の場合に例外とする() {

        exception.expect(DateTimeParseException.class);

        LocalDate date = LocalDate.parse("2007/09/31", DateTimeFormatter.ofPattern("yyyy/MM/dd").withResolverStyle(ResolverStyle.STRICT));
        System.out.print(date);
    }

    @Test
    public void LocalDateTime_日付文字列をパースする_存在しない日時の場合に例外とするがフォーマットは厳密にチェックしない() {
        LocalDate date = LocalDate.parse("2007/9/001", DateTimeFormatter.ofPattern("uuuu/M/d").withResolverStyle(ResolverStyle.STRICT));
        System.out.print(date);
    }

    @Test
    public void Date_日付文字列をパースする_存在しない日時の解釈がSimpleDateFormatと異なる() {
        try {
            Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2007/09/31 23:00:02");
            LocalDateTime ldt = LocalDateTime.parse("2007/09/31 23:00:02", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            Date actual = Date.from(ldt.toInstant(ZoneId.systemDefault().getRules().getOffset(ldt)));

            assertThat(actual, is(not(date)));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void LocalDate_日付文字列をパースする_フォーマットは厳密に一致していないと例外を投げる() {

        exception.expect(DateTimeParseException.class);

        LocalDate date = LocalDate.parse("2007/9/001", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        System.out.print(date);
    }

    @Test
    public void ZonedDateTime_日付文字列をパースする_ISO8601に準拠した文字列() {
        ZonedDateTime date = ZonedDateTime.parse("2007-12-03T10:15:30+09:00[Asia/Tokyo]");
        System.out.print(date);
    }

    @Test
    public void ZonedDateTime_日付文字列をパースする_ISO8601に準拠していない文字列の場合は例外() {

        exception.expect(DateTimeParseException.class);

        ZonedDateTime date = ZonedDateTime.parse("2014/01/01 10:00:02", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        System.out.print(date);
    }

    @Test
    public void ZonedDateTime_日付文字列をパースする_ISO8601に準拠していない文字列の場合は例外_ロケール指定してもダメ() {

        exception.expect(DateTimeParseException.class);

        ZonedDateTime date = ZonedDateTime.parse("2014/01/01 10:00:02", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.JAPAN));
        System.out.print(date);
    }

}
