package com.heitian.ssm.redis;

import redis.clients.jedis.Jedis;

/**
 * @version 1.0
 * @类名: redisTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-8-10
 */
public class redisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println("连接成功");
        System.out.println("服务正在运行：" + jedis.ping());
    }
}
