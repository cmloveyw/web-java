/*
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved
 */

package com.heitian.ssm.test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * aa :
 *
 * @author chenming
 * @version 1.00
 * @since 2018-02-08 9:48
 */
public class dateTest {

    private static HashMap<Integer, String> map = new HashMap<Integer, String>(16, 0.75f);

    public static void main(String[] args) {
        map.put(0, "C");
        map.put(1, "d");
        map.put(2, "e");
        map.put(3, "f");
        map.put(4, "g");
//        new Thread("Thread1") {
//            public void run() {
//                map.put(5, "h");
//                map.put(7, "B");
//
//                System.out.println(map.get(11));
//                System.out.println(map);
//            }
//
//            ;
//        }.start();
//        new Thread("Thread2") {
//            public void run() {
//                map.put(6, "A");
//                System.out.println(map);
//            }
//
//            ;
//        }.start();
        for (Integer a : map.keySet()) {
            System.out.println(a + "-" + map.get(a));
        }

        for (Map.Entry<Integer, String> a : map.entrySet()) {
            System.out.println(a.getKey() + "-" + a.getValue());
        }

        Instant instant = Instant.ofEpochSecond(1518060019);
        Instant instant2 = Instant.ofEpochSecond(1758060019);
        String day = instant.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.BASIC_ISO_DATE);//20180208+0800
        String day1 = instant.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE);//2018-02-08
        String day2 = instant2.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE);//2018-02-08
        String day3 = instant.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE);//2018-02-08+08:00

        System.out.println(day);
        System.out.println(day1);
        System.out.println(day2);

//        LocalDate today = LocalDate.parse("2018-02-09");
//        LocalDate today2 = LocalDate.parse("2019-04-28");
        LocalDate today = LocalDate.parse(day1);
        LocalDate today2 = LocalDate.parse(day2);
        System.out.println("Today : " + today);
        Period p = Period.between(today, today2);
        String between = p.getYears() + "年" + p.getMonths() + "月" + p.getDays() + "天";
        System.out.println(between);
        System.out.println(today2.toEpochDay() - today.toEpochDay());//计算相差多少天

        long daysDiff = ChronoUnit.DAYS.between(today, today2);//计算相差多少天
        long daysDiff2 = ChronoUnit.WEEKS.between(today, today2);//计算相差多少周
        System.out.println(daysDiff);
        System.out.println(daysDiff2);

        LocalDateTime
                arrivalDate = LocalDateTime.now();
        String a = arrivalDate.format(DateTimeFormatter.ISO_DATE);
        System.out.println(arrivalDate);
        System.out.println(a);

    }
}
