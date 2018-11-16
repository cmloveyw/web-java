package com.heitian.ssm.redis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * @version 1.0
 * @类名: redisKeyTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-8-10
 */
public class redisKeyTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        Set<String> keys = jedis.keys("*");
        keys.stream().forEach(item -> {
            System.out.println(item);
        });

        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println(key);
        }
    }
}
