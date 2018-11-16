package test;/*
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved
 */

import com.google.common.collect.Maps;

import common.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * bb :
 *
 * @author chenming
 * @version 1.00
 * @since 2017-09-19 19:45
 */
public class bb {
    public static void main(String args[]) {
        Student student = new Student("小路1/", 23);
        Student student2 = new Student("小路'", 23);
        List<Student> list = new ArrayList();
        list.add(student);
        list.add(student2);
        Map<Object, Object> map = Maps.newHashMap();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .forEach(item -> {
                    map.put(item.getName(), item.getAge());
                });

        String result = JsonUtils.toJson(map);
        System.out.println(result);
    }
}
