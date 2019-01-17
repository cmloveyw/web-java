package test.digui;

/**
 * @version 1.0
 * @类名: Test.java
 * @描述: 5*4*3*2*1(递归：阶乘，累计求和，删除文件，拷贝文件，计算文件大小)
 * @创建人: CM
 * @创建时间: 2019-1-17
 */
public class Test {

    public static void main(String[] args) {
        //5*4*3*2*1
        int num = 5000;
        System.out.println(getJc(num));
    }

    public static int getJc(int num) {
        if (num == 1 || num == 0) {
            return 1;
        } else {
            return num + getJc(num - 1);
        }
    }
}
