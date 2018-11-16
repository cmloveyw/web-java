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
public class redisHashXingNengTest {
    //private static final Cache testCache = Redis.use(Cons.TEST) ;
    private static final Jedis jedis = new Jedis("localhost");
    private static int C = 0;
    //循环1百万次
    private static final int ROUND = 1000000;

    //生成1百万个User，用hset哈希命令保存对象的各个属性到1百万个Key中
    public static void setMap() {
        System.out.println("setMap ...");
        C = 0;
        long t = System.currentTimeMillis();
        String key;
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < ROUND; i++) {
            key = "userMap" + C;
            map.put("userId", C + "");
            //map.put("adminFlag", m.getAdminFlag());
            map.put("avatar", "a" + C);
            map.put("nickname", "n" + C);
            jedis.hmset(key, map);
            C++;
        }
        long now = System.currentTimeMillis();
        System.out.println("setMap use " + (now - t) / 1000 + "seconds");
    }

    //用hgetAll哈希命令把1百万个Key的所有field读出来，还原成1百万个User
    public static void readMap() {
        System.out.println("readMap ...");
        C = 0;
        long t = System.currentTimeMillis();
        String key;
        Map<String, String> map;
        for (int i = 0; i < ROUND; i++) {
            key = "userMap" + C;
            //map = jedis.hgetAll(key);
            jedis.del(key);
            //System.out.println(map.get("userId") + ", " + map.get("avatar") + ", " + map.get("nickname"));
            C++;
        }
        long now = System.currentTimeMillis();
        System.out.println("readMap use " + (now - t) / 1000 + "seconds");
    }

    public static void main(String[] args) {
        //setMap();
        readMap();
    }
}
