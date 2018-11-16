/*
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved
 */

package com.heitian.ssm.test;


import java.util.Arrays;
import java.util.List;

/**
 * bb :
 *
 * @author chenming
 * @version 1.00
 * @since 2017-12-23 15:19
 */
public class bb {
    String name = "1,2,3,4,5";
    String[] names = {"Fred Edwards", "Anna Cox", "Deborah Patterson", "Ruth Torres", "Shawn Powell",
            "Rose Thompson", "Rachel Barnes", "Eugene Ramirez", "Earl Flores", "Janice Reed", "Sarah Miller",
            "Patricia Kelly", "Carl Hall", "Craig Wright", "Martha Phillips", "Thomas Howard", "Steve Martinez",
            "Diana Bailey", "Kathleen Hughes", "Russell Anderson", "Theresa Perry"};

    String[] aa = name.split(",");

    public static void main(String[] are) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, null, 3, 4, 4, 9);
        Integer sum = numbers.stream()
                .filter(item -> item != null)
                .distinct()
                .reduce(2, (a, b) -> a + b);
        System.out.println(sum);
    }
}
