package com.edge.core.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CoreDateUtils {

    static SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static SimpleDateFormat uploadDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    static SimpleDateFormat mongoDbClause = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static String dateToStandardSting(Date date) {
        return standardDateFormat.format(date);
    }

    public static long getDaysBetweenDates(Date d1, Date d2) {
        return TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());
    }

    public static Date parseDate(String date) throws ParseException {
        return uploadDateFormat.parse(date);
    }

    public static Date parseStandardDate(String date) throws ParseException {
        return standardDateFormat.parse(date);
    }

    public static LocalDate toLocalDate(Date input) {
        Instant instant = Instant.ofEpochMilli(input.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    public static int calculateAge(Date birthDateInp) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = toLocalDate(birthDateInp);
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }


    public static long calculateDays(Date someDateInp) {
        return ChronoUnit.DAYS.between(someDateInp.toInstant(), new Date().toInstant());
    }

    public static boolean isPast(Date dateInput) {
        return !isTodayOrFuture(dateInput);
    }

    public static boolean isTodayOrFuture(Date dateInput) {
        return getDaysBetweenDates(dateInput, today()) >= 0;
    }

    public static boolean isAdult(Date birthDateInp) {
        return calculateAge(birthDateInp) >= 18;
    }

    public static Date today() {
        return new Date();
    }

    public static String dateToMongoDbClause(Date date) {
        return mongoDbClause.format(date);
    }

    public static void main(String[] args) throws ParseException {
        Date date = parseStandardDate("2020-09-11");

        System.out.println(calculateDays(date));
        System.out.println(isTodayOrFuture(date));

        //System.out.println(calculateAge(date));
        //System.out.println(isAdult(date));
    }

    public static Date addDays(Date dateInput, int cnt) {
        return DateUtils.addDays(dateInput, cnt);
    }

    public static Date yesterday() {
        return addDays(today(), -1);
    }
}