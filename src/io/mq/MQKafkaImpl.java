package io.mq;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import utils.redisutils.JedisUtils;
import utils.redisutils.KafkaProducerTest;

public class MQKafkaImpl implements MQ<JSONObject> {
	public static final String LIST_NAME="list1";	//redis中队列的名称
	public static final String WRONG_LIST_NAME="list2";	//redis中错误队列的名称
	
	
	private final Producer<String, String> producer;
	public final String TOPIC = "topic1";

	public MQKafkaImpl() {
		Properties props = new Properties();
		// 此处配置的是kafka的端口
//		props.put("metadata.broker.list", "127.0.0.1:9092");
//		props.put("metadata.broker.list", "192.168.181.138:9092");
//		props.put("zk.connect", "192.168.181.138:2181");  
		props.put("metadata.broker.list", "192.168.159.150:9092");
		props.put("zk.connect", "192.168.159.150:2181");
		// 配置value的序列化类
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// 配置key的序列化类
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "-1");
		producer = new Producer<String, String>(new ProducerConfig(props));
	}

	/**
	 * 此方法不会抛出异常
	 */
	public boolean produce(String key,String data){
		try{
			producer.send(new KeyedMessage<String, String>(TOPIC, key, data));
		}catch(Exception e){
			System.err.println("向kafka中写入时出错");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
			return false;
		}
		return true;
	}
	
	/**
	 * ------------------------------------下面的方法是接口中定义的-------------------------------------------
	 */
	@Override
	public boolean put(String topicName, JSONObject msg) {
		boolean result = this.produce("key", msg.toString());
		return result;
	}
	@Override
	public JSONObject get(String topicName) {
		return null;
	}
	@Override
	public List<JSONObject> get(String topicName, int count) {
		return null;
	}
	@Override
	public long size(String topicName) {
		return 0;
	}
	@Override
	public void restore(String topicName, JSONObject t) {
		
	}

}
