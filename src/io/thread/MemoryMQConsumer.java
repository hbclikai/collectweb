package io.thread;

import com.alibaba.fastjson.JSONObject;

import io.memorymq.MemoryMQ;
import io.mq.MQ;
import io.mq.MQRedisImpl;
import parse.parser.RawBytesChecker;

public class MemoryMQConsumer extends Thread {
	private MQ<JSONObject> mq;
	private MemoryMQ<JSONObject> memoryMQ;

	public MemoryMQConsumer(MQ<JSONObject> mq, MemoryMQ<JSONObject> memoryMQ) {
		super("MemoryMQConsumer");
		this.mq = mq;
		this.memoryMQ = memoryMQ;
	}

	@Override
	public void run() {
		System.out.println("start");
		while (true) {
			// 1.从队列中取出
			JSONObject jsonObj = memoryMQ.get();	//阻塞
			// 2.检验
			String listName = null; // 要保存到redis中的list的名字
			if (RawBytesChecker.check(jsonObj) == 0) { // 消息格式正确,通过检查
				listName = MQRedisImpl.LIST_NAME;
			} else { // 没有通过检查
				listName = MQRedisImpl.WRONG_LIST_NAME;
			}
			// 3.写入到redis中
			boolean insertResult = mq.put(listName, jsonObj);
			// 4.如果保存失败,就把消息复原到内存队列中
			if (insertResult == false) {
				memoryMQ.restore(jsonObj);
			}
		}
	}
}