package com.yangls.miaosha.redis;

import com.yangls.miaosha.common.CommonUtils;
import com.yangls.miaosha.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

@Service
public class RedisService {
	
	@Autowired
	JedisPool jedisPool;
	
	/**
	 * 获取当个对象
	 * */
	public <T> T get(KeyPrefix prefix, String key,  Class<T> clazz) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 //生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 String  str = jedis.get(realKey);
			 T t =  stringToBean(str, clazz);
			 return t;
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 设置对象
	 * */
	public <T> boolean set(KeyPrefix prefix, String key,  T value) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 String str = beanToString(value);
			 if(str == null || str.length() <= 0) {
				 return false;
			 }
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 int seconds =  prefix.expireSeconds();
			 if(seconds <= 0) {
				 jedis.set(realKey, str);
			 }else {
				 jedis.setex(realKey, seconds, str);
			 }
		 }finally {
			  returnToPool(jedis);
		 }
		return true;
	}
	
	/**
	 * 判断key是否存在
	 * */
	public <T> boolean exists(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.exists(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 增加值
	 * */
	public <T> Long incr(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.incr(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 减少值
	 * */
	public <T> Long decr(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.decr(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}


	public boolean delete(KeyPrefix prefix) {
		if(prefix == null) {
			return false;
		}
		List<String> keys = scanKeys(prefix.getPrefix());
		if(keys==null || keys.size() <= 0) {
			return true;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(keys.toArray(new String[0]));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}

	public List<String> scanKeys(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> keys = new ArrayList<>();
			String cursor = "0";
			ScanParams sp = new ScanParams();
			sp.match("*"+key+"*");
			sp.count(100);
			do{
				ScanResult<String> ret = jedis.scan(cursor, sp);
				List<String> result = ret.getResult();
				if(result!=null && result.size() > 0){
					keys.addAll(result);
				}
				//再处理cursor,这里可能有错原来是：cursor = ret.getStringCursor();但是没有这个方法
				cursor = new String(ret.getCursorAsBytes());
			}while(!cursor.equals("0"));
			return keys;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public static <T> String beanToString(T value) {
		return CommonUtils.beanToString(value);
	}

	@SuppressWarnings("unchecked")
	private <T> T stringToBean(String str, Class<T> clazz) {
		return CommonUtils.stringToBean(str,clazz);
	}

	private void returnToPool(Jedis jedis) {
		 if(jedis != null) {
			 jedis.close();
		 }
	}

}
