/*
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved
 */

package com.heitian.ssm.test;

import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * aa :java8时间的操作
 *
 * @author chenming
 * @version 1.00
 * @since 2017-12-08 10:46
 */
public class aa {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = Lists.newArrayList("2017-12-07", "2017-01-07", "2017-02-07");
        list.stream().forEach(item -> {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(format.parse(item));
                calendar.add(Calendar.MONTH, -1);
                int aa = calendar.get(Calendar.MONTH) + 1;
                System.out.println(calendar.get(Calendar.YEAR) + "-" + aa);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
        // double price = 405 / 1.17;

        list.stream().forEach(item -> {
            LocalDate date = LocalDate.parse(item, DateTimeFormatter.ISO_LOCAL_DATE);
            date = date.minusMonths(1);
            long toEpochDay = date.toEpochDay();
            System.out.println(date.getYear() + "-" + date.getMonthValue());
            System.out.println(date.toString());
            System.out.println(toEpochDay);
        });

        //一个时间是否在另一个时间之前、之后
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusMonths(1);
        boolean isBefore = LocalDate.parse(date1.toString()).isBefore(LocalDate.parse(date2.toString()));
        boolean isAfter = LocalDate.parse(date2.toString()).isAfter(LocalDate.parse(date2.toString()));
        System.out.println("isBefore: " + isBefore);
        System.out.println("isAfter: " + isAfter);


        List<Long> times = timeInterval(list, LocalDate.now().toString());
        System.out.println(times);
    }


    private static List<Long> timeInterval(List<String> ledgerDataDoList, String beginTime) {
        List<Long> timeOut = Lists.newArrayList();
        Optional.ofNullable(ledgerDataDoList).orElse(new ArrayList<>()).stream()
                .forEach(item -> {
                    timeOut.add(LocalDate.parse(item).toEpochDay() - LocalDate.parse(beginTime).toEpochDay());

//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(item - beginTime));
//                    int year = calendar.get(Calendar.YEAR) - 1970;
//                    int month = calendar.get(Calendar.MONTH);
//                    int date = calendar.get(Calendar.DATE) - 1;
//                    String time = year + "年" + month + "个月" + date + "天";
//                    item.setExpiryTime(time);
                });
        return timeOut;

    }
}
