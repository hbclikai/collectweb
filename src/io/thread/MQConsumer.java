package io.thread;

import io.db.DB;
import io.mq.MQ;
import io.mq.MQRedisImpl;

import java.util.ArrayList;
import java.util.List;

import parse.bean.AbstractMessage;
import parse.parser.NBPlatformMessagePaser;

import com.alibaba.fastjson.JSONObject;

public class MQConsumer extends Thread {
	private MQ<JSONObject> mq;
	private DB<AbstractMessage> db;

	public MQConsumer(MQ<JSONObject> mq, DB<AbstractMessage> db) {
		super("MQConsumer");
		this.mq = mq;
		this.db = db;
	}

	@Override
	public void run() {
		while (true) {
			// step1.取出数据,并解析
			List<JSONObject> list = mq.get(MQRedisImpl.LIST_NAME, 20);
			List<AbstractMessage> msgs=new ArrayList<AbstractMessage>();
			for (JSONObject jsonObj : list) {	//把List<JSONObject>转换成List<Message>
				List<AbstractMessage> absMsgs = NBPlatformMessagePaser.parse(jsonObj);
				for (AbstractMessage abstractMessage : absMsgs) {
					msgs.add(abstractMessage);
				}
			}
			// step2.保存
			boolean saveResult = db.save(msgs);	// 保存到数据库
			
			// step3.如果保存失败,就恢复数据,然后睡一会
			if (!saveResult) {		
				for(JSONObject obj:list){	//恢复所有的消息
					mq.restore(MQRedisImpl.LIST_NAME, obj);
				}
				try {	//失败了就睡会觉
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			// step4.检查刚才保存了多少条,如果数量太少,就睡会觉
			if(list.size()<20){
				try {	
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
