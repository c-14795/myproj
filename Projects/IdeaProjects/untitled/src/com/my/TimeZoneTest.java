package com.my;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.ChronoUnit.*;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;

public class TimeZoneTest {

    public static void main(String[] args) {
        args = new String[4];
        args[0] = "2017";
        args[1] = "11";
        args[2] = "4";
        args[3] = "1";

        String  dateFormat = "yyyy-MM-dd";
        String date = args[0]+"-"+args[1]+"-"+"01";
        LocalDate start_date = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
        System.out.println(start_date.plusWeeks(Integer.parseInt(args[2])-1).toString());
        System.out.println(getDate(4, 1, 11, 2017).toString());
        System.exit(5);
        LocalDate intial= LocalDate.of(Integer.parseInt(args[0]),Integer.parseInt(args[1]),1);
        LocalDate end_date = intial.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(end_date.format(DateTimeFormatter.ofPattern(dateFormat)));
        System.out.println(DAYS.between(start_date, end_date));
        LocalDate updatedDate = start_date;

        for(int i =0 ;i<DAYS.between(start_date, end_date); i++)
        {

            System.out.println(updatedDate.format(DateTimeFormatter.ofPattern(dateFormat)));
            System.out.println(updatedDate.format(DateTimeFormatter.ofPattern("W")));
            System.out.println(updatedDate.format(DateTimeFormatter.ofPattern("e")));
            updatedDate = updatedDate.plusDays(1);
        }

    }


    public static LocalDate getDate(int weekOfMonth, int dayOfWeek, int month, int year) {
        // you can customize your week definition (first day of week and mininum days in first week)
        WeekFields wf = WeekFields.of(DayOfWeek.MONDAY, 1);

        // Sunday is 0, adjusting value
        DayOfWeek dow = DayOfWeek.of(dayOfWeek);

        // get the first weekday of the month
        LocalDate first = LocalDate.of(year, month, 1).with(TemporalAdjusters.firstDayOfMonth());

        System.out.println(first.toString());

        // check in which week this date is
        int week = first.get(wf.weekOfMonth());

        System.out.println(week);

        // adjust to weekOfMonth
        return first.plusWeeks(weekOfMonth - week);
    }

}
