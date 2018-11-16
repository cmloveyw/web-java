package com.heitian.ssm.redis;

import redis.clients.jedis.Jedis;

/**
 * @version 1.0
 * @类名: redisStringTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-8-10
 */
public class redisStringTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        jedis.set("cm", "啦啦啦");
        System.out.println("String存储值为：" + jedis.get("cm"));
    }
}
