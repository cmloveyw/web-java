package test.redis;

/**
 * @version 1.0
 * @类名: Test.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-12-27
 */
public class Test {
    public static void main(String[] args) {
        Service service = new Service();
        for (int i = 0; i < 50; i++) {
            SeckillTest seckillTest = new SeckillTest(service);
            seckillTest.start();
        }
    }
}
