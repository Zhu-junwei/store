package com.store.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	
	//定义JedisPool对象
	private static JedisPool jedisPool = null ;
	
	/**
	 * 加载配置文件，对JedisPool对象初始化
	 */
	static {

		//加载配置文件
		InputStream is = JedisUtil.class.getClassLoader().getResourceAsStream("redis.properties");
		Properties pro = new Properties();
		try {
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//对JedisPool对象初始化
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty("redis.maxIdle")));//最大闲置个数
		poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("redis.minIdle")));//最小闲置个数
		poolConfig.setMaxTotal(Integer.parseInt(pro.getProperty("redis.maxTotal")));//最大连接数
		
		jedisPool = new JedisPool(poolConfig,pro.getProperty("redis.url"),Integer.parseInt(pro.getProperty("redis.port")));
		
		
	}
	
	/**
	 * 获取连接对象
	 * @return 返回Jedis对象
	 */
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 释放连接资源，不关闭连接池
	 * @param jedis 传过来的Jedis对象
	 */
	public static void close(Jedis jedis) {
		jedis.close();
	}
	
	/**
	 * 释放连接资源,并关闭连接池
	 * @param jedis 传过来的Jedis对象
	 */
	public static void closeAll(Jedis jedis) {
		jedis.close();
		jedisPool.close();
	}
}
