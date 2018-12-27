package test.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @version 1.0
 * @类名: Service.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-12-27
 */
public class Service {
    private static JedisPool pool = null;
    private DistributedLock lock = new DistributedLock(pool);
    int n = 500;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(8);
        config.setMaxWaitMillis(1000 * 10);
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "127.0.0.1", 6379, 3000);
    }

    public void seckill() {
        String identifier = lock.lockWithTimeout("resource", 5000, 1000);
        System.out.println("identifier" + identifier);
        System.out.println(Thread.currentThread().getName() + "获得了锁");
        System.out.println(--n);
        lock.releaseLock("resource", identifier);
    }

}
