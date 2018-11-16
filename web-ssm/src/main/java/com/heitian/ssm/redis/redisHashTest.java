package com.heitian.ssm.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @类名: redisHashTest.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-8-10
 */
public class redisHashTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        Map<String, String> map = new HashMap<>();
        map.put("userName", "cm");
        map.put("password", "123456");
        map.put("age", "21");
        jedis.hmset("cmMap", map);
        map = jedis.hgetAll("cmMap");
        map.forEach((k, v) -> {
            System.out.println(k + "##" + v);
        });

    }
}
