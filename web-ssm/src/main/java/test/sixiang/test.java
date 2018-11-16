package test.sixiang;

/**
 * @version 1.0
 * @类名: test.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-11
 */
public class test {
    public static void main(String[] args) {
        student a = new student();
        a.setAge(13);
        int result = a.hashCode();
        System.out.println(result);
    }
}
