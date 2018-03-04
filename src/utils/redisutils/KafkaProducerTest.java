package utils.redisutils;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducerTest {
	private final Producer<String, String> producer;
	public final String TOPIC = "topic1";

	public KafkaProducerTest() {
		Properties props = new Properties();
		// 此处配置的是kafka的端口
//		props.put("metadata.broker.list", "127.0.0.1:9092");
		props.put("metadata.broker.list", "192.168.1.63:9092");
		props.put("zk.connect", "192.168.1.60:2181,192.168.1.61:2181,192.168.1.62:2181");  
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

	public static void main(String[] args) {
		KafkaProducerTest p=new KafkaProducerTest();
		for (int i = 0;; i++) {
			String key = "key" + i;
			String data = key + "INFO JobScheduler: Finished job streaming job 1493090727000 ms.0 from job set of time 1493090727000 msINFO JobScheduler: Finished job streaming job 1493090727000 ms.0 from job set of time 1493090727000 msINFO JobScheduler: Finished job streaming job 1493090727000 ms.0 from job set of time 1493090727000 msINFO JobScheduler: Finished job streaming job 1493090727000 ms.0 from job set of time 1493090727000 msINFO JobScheduler: Finished job streaming job 1493090727000 ms.0 from job set of time 1493090727000 msINFO JobScheduler: Finished job streaming job 1493090727000 ms.0 from job set of time 1493090727000 ms";
			p.produce(key, data);
			
			if(i%1000==0){
				System.out.println("已生产"+i);
			}
		}
	}
}
