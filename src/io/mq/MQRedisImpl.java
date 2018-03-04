package io.mq;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import utils.redisutils.JedisUtils;

public class MQRedisImpl implements MQ<JSONObject> {
	public static final String LIST_NAME="list1";	//redis中队列的名称
	public static final String WRONG_LIST_NAME="list2";	//redis中错误队列的名称
	
	@Override
	public boolean put(String topicName, JSONObject msg) {
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getJedis();
			jedis.lpush(topicName, msg.toString());
		} catch (Exception e) {
			try {
				Thread.sleep(2000);		//一旦出错就过2秒再试,防止频繁重复连接
			} catch (InterruptedException e1) {
			}
			System.err.println("MQRedisImpl无法连接到redis");
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return true;
	}

	/**
	 * 根据list的名字确定要从哪个队列中取值
	 */
	@Override
	public JSONObject get(String listName) {
		Jedis jedis = null;
		JSONObject jsonObj=null;
		try {
			jedis = JedisUtils.getJedis();
			String json = jedis.rpop(listName);
			if (json == null) {
				return null;
			}
			jsonObj = JSONObject.parseObject(json);
		} catch (Exception e) {
			try {
				Thread.sleep(2000);		//一旦出错就过2秒再试,防止频繁重复连接
			} catch (InterruptedException e1) {
			}
			System.err.println("MQRedisImpl无法连接到redis");
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return jsonObj;
	}
	
	/**
	 * 一次取出多条,count是要取出的条数
	 * <p>这个方法只是循环执行上面那个get()方法 
	 */
	@Override
	public List<JSONObject> get(String listName, int count) {
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for (int i = 0; i < count; i++) { 	// 取count次
			JSONObject jsonObj = get(listName);
			if (jsonObj == null) { 	// 如果没有了,就取出多少返回多少
				return list;
			}
			list.add(jsonObj);
		}
		return list;
	}

	@Override
	public long size(String listName) {
		Jedis jedis = null;
		long size = 0;
		try {
			jedis = JedisUtils.getJedis();
			size = jedis.llen(listName);
		} catch (Exception e) {
			System.err.println("MQRedisImpl无法连接到redis");
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return size;
	}

	@Override
	public void restore(String topicName, JSONObject msg) {
		this.put(topicName, msg); // restore()实际就是调用put()方法
	}
}
