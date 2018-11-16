package com.heitian.ssm.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @version 1.0
 * @类名: redisListTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-8-10
 */
public class redisListTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        /*jedis.lpush("s-list", "cm");
        jedis.lpush("s-list", "cmm");
        jedis.lpush("s-list", "lyw");
        jedis.lpush("s-list", "xww");*/
        List<String> list = jedis.lrange("s-list", 0, 10);
        list.stream().forEach(item -> {
            System.out.println(item);
        });
    }
}
