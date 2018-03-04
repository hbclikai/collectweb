package io.thread;

import io.db.DB;
import io.db.DBHbaseImpl;
import io.memorymq.MemoryMQ;
import io.memorymq.MemoryMQImpl;
import io.mq.MQKafkaImpl;
import parse.bean.AbstractMessage;

import com.alibaba.fastjson.JSONObject;

/**
 * 主类,spring容器启动时会加载它,从而触发static中的代码
 */
public class Server {
	// 创建MemoryMQ和MQ和DB
	public static MemoryMQ<JSONObject> memoryMQ = new MemoryMQImpl<JSONObject>();
	public static DB<AbstractMessage> db=new DBHbaseImpl();
			
	// 接收了多少个来自Controller给的JSONObject
	public static long controllerTimes = 0L;
	public static void addMessage(JSONObject msg) {
		memoryMQ.put(msg);
		controllerTimes++;
	}

	/*
	 * 在static块中启动所有线程
	 */
	static {
		System.out.println("Server is loaded...");
		// 1.启动MemoryMQConsumer
		for(int i=0;i<10;i++) {	
			//两个参数, 前者是new出来的,后者是传进去的,原因在于,前者是一个线程需要一个, 后者是所有线程共享一个
			new MemoryMQConsumer(new MQKafkaImpl(), memoryMQ).start();
		}
		
		// 2.启动MQConsumer
		for(int i=0;i<1;i++) {
			new MQConsumer(new MQKafkaImpl(), db).start();
		}
		
		// //3.监听线程
		new ServerStatusListener().start();
		
	}

}
