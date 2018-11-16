package com.heitian.ssm.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @类名: test.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-5-16
 */
public class test {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dataList = new ArrayList<String>();
        try {
            Date d = new Date();
            Calendar cld = Calendar.getInstance();
            cld.setTime(d);
            for (int i = 1; i <= 12; i++) {
                cld.add(Calendar.MONTH, 1);
                d = cld.getTime();
                String time = sdf.format(d);
                dataList.add(time);
                System.out.println(time);
            }
        } catch (Exception e) {

        }
    }
}
