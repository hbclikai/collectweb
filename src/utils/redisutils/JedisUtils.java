package utils.redisutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	@Test
	public void test(){
		Jedis jedis = JedisUtils.getJedis();
		jedis.set("key1", "value1");
		System.out.println(jedis.get("key1")); //如果打印出value1就说明验证成功
		jedis.close();
	}
	
	private static JedisPool pool=null;
	static {
		//1加载properties配置文件
		InputStream is = JedisUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
		Properties pp=new Properties();
		try {
			pp.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//2读取配置文件中的值
		int maxIdel=Integer.parseInt(pp.getProperty("redis.maxIdle"));
		int minIdle=Integer.parseInt(pp.getProperty("redis.minIdle"));
		int maxTotal=Integer.parseInt(pp.getProperty("redis.maxTotal"));
		String ip=pp.getProperty("redis.ip");
		int port=Integer.parseInt(pp.getProperty("redis.port"));
		//3创建连接池的配置对象
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxIdle(maxIdel);	//闲置的个数如果超过30, 就关闭一些
		config.setMinIdle(minIdle);	//闲置的不足10个,就再开几个
		config.setMaxTotal(maxTotal); //最大连接数
		//4根据config创建连接池
		pool=new JedisPool(config,ip,port);
	}
	
	public static Jedis getJedis() {
		//从连接池中获取jedis
		Jedis jedis=pool.getResource();
		return jedis;
	}
	
}
