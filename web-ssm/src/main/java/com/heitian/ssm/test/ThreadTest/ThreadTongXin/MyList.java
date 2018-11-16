package com.heitian.ssm.test.ThreadTest.ThreadTongXin;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @类名: MyList.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-10-30
 */
public class MyList {
    /*private List myList = new ArrayList();

    @SuppressWarnings("unchecked")
    void add() {
        myList.add("我是元素");
    }

    public int size() {
        return myList.size();
    }*/

    private static List list = new ArrayList();

    public static void add() {
        list.add("我是元素");
    }

    public static int size() {
        return list.size();
    }
}



