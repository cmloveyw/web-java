package test.duoxianchenganli;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @类名: InfiniteRecursion.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-8
 */
public class InfiniteRecursion {
    public String toString() {
        //应该调用Object.toString()方法，所以此处应为super.toString()。
        //return " InfiniteRecursion address: " + this + "\n";
        return " InfiniteRecursion address: " + super.toString() + "\n";
    }

    public static void main(String[] args) {
        List<InfiniteRecursion> v = new ArrayList<InfiniteRecursion>();
        for (int i = 0; i < 10; i++)
            v.add(new InfiniteRecursion());
        System.out.println(v);
    }
}
