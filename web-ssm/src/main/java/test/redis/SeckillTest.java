package test.redis;

/**
 * @version 1.0
 * @类名: SeckillTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-12-27
 */
public class SeckillTest extends Thread {
    private Service service;

    public SeckillTest(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.seckill();
    }

}
