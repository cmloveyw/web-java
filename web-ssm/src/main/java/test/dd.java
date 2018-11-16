/*
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved
 */

package test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * dd :
 *
 * @author chenming
 * @version 1.00
 * @since 2017-11-01 9:22
 */
public class dd {
    public static void main(String args[]) {
        Calendar curr = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        curr.setTime(date);
//        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        curr.add(Calendar.YEAR, +1);
        //curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + 1);
        date = curr.getTime();

        System.out.println(date);

        Map map = new HashMap();
        map.isEmpty();
    }
}
