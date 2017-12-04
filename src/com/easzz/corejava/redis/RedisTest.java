package com.easzz.corejava.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by easzz on 2017/11/10 12:48
 */
public class RedisTest {
	public static void main(String[] args){
		Jedis jedis=new Jedis("122.97.255.19",6379);
		jedis.set("user","mike");
		System.out.println(jedis.get("user"));
		jedis.close();
	}
}
