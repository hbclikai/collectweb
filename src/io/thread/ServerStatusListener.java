package io.thread;

import io.mq.MQRedisImpl;
import parse.utils.CodecUtils;
import redis.clients.jedis.Jedis;
import utils.redisutils.JedisUtils;

public class ServerStatusListener extends Thread{
	//1 时间戳
	private long previousTimeMillis=0;
	private long timeMillis=0;
	//2 Controller给的所有的消息的数量
	private long previousControllerTimes=0;
	private long controllerTimes=0;
	//3 内存MQ长度
	private int previousMessageQueueSize=0;
	private int messageQueueSize=0;
	//4 redis中list的长度
	private long previousRedisListSize=0;
	private long redisListSize=0;
	//5 redis中错误list的长度
	private long previousWrongRedisListSize=0;
	private long wrongRedisListSize=0;
	
	public ServerStatusListener(){
		super("StatusListener");
	}
	@Override
	public void run() {
		while(true){
			timeMillis=System.currentTimeMillis();
			controllerTimes=Server.controllerTimes;
			messageQueueSize=Server.memoryMQ.size();
			//MQRedisImpl redis=new MQRedisImpl();
			//redisListSize = redis.size(MQRedisImpl.LIST_NAME);
			//wrongRedisListSize = redis.size(MQRedisImpl.WRONG_LIST_NAME);
			//step1.打印数量-------------------------------------------------
			//1 接收Controller请求数量
			System.out.print("来自Controller:"+controllerTimes+"\t\t");
			//2 MQ长度
			System.out.print("内存MQ长:"+messageQueueSize+"\t\t");
			//3 redis中list的长度
			System.out.print("Redis长:"+redisListSize+"\t\t");
			//4 redis中错误list的长度
			System.out.print("Redis错误长:"+wrongRedisListSize+"\n");
			
			//step3.打印速度------------------------------------------------
			double v1=(controllerTimes-previousControllerTimes)*1000.0/(timeMillis-previousTimeMillis);
			double v2=(messageQueueSize-previousMessageQueueSize)*1000.0/(timeMillis-previousTimeMillis);
			double v3=(redisListSize-previousRedisListSize)*1000.0/(timeMillis-previousTimeMillis);
			double v4=(wrongRedisListSize-previousWrongRedisListSize)*1000.0/(timeMillis-previousTimeMillis);
			System.out.print("v:"+CodecUtils.toDecimal1(v1));
			System.out.print("\t\tv:"+CodecUtils.toDecimal1(v2));
			System.out.print("\t\tv:"+CodecUtils.toDecimal1(v3));
			System.out.print("\t\tv:"+CodecUtils.toDecimal1(v4)+"\n");
			
			//step3.更新上一次的值--------------------------------------------
			previousTimeMillis=timeMillis;
			previousControllerTimes=controllerTimes;
			previousMessageQueueSize=messageQueueSize;
			previousRedisListSize=redisListSize;
			previousWrongRedisListSize=wrongRedisListSize;
			
			//step4.睡觉------------------------------------------------
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}