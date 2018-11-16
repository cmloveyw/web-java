/*
 * Copyright (c) 2014-2017 墨博云舟 All Rights Reserved
 */

package test;

import java.util.List;

import lombok.Data;

/**
 * Student :
 *
 * @author chenming
 * @version 1.00
 * @since 2017-10-17 17:57
 */
@Data
public class Student {
    private String name;
    private Integer age;

    private List<String> image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Student() {
    }
}
