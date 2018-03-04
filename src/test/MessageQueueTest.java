package test;

import io.db.DB;
import io.db.DBMysqlImpl;
import io.memorymq.MemoryMQImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import parse.bean.AbstractMessage;
import parse.bean.MessageB0;


public class MessageQueueTest {
	public static MemoryMQImpl<AbstractMessage> queue;

	public static void main(String[] args) {
		DB dbInserter =new DBMysqlImpl();
		MessageQueueTest.queue = new MemoryMQImpl<AbstractMessage>();
		new StatusViewer().start();
		ExecutorService producerPool = Executors.newFixedThreadPool(10);
		ExecutorService consumerPool = Executors.newFixedThreadPool(10);
		for(int i=0;i<20;i++){
			producerPool.submit(new Producer("Producer"+i));
		}
		for(int i=0;i<15;i++){
			consumerPool.submit(new Consumer("Consumer" + i, MessageQueueTest.queue,	dbInserter));
		}
	}
}

class Producer extends Thread {
	public Producer(String name) {
		super(name);
	}

	@Override
	public void run() {
		int times = 0;
		while (true) {
			AbstractMessage msg = new MessageB0();
			try {
				MessageQueueTest.queue.put(msg);
				//Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			times++;
			if(times%5000==0){
				//System.out.println(this.getName()+"已经生产:"+times+"个Message");
			}
		}
	}
}

class StatusViewer extends Thread {
	@Override
	public void run() {
		while(true){
			System.out.println("队列长度:"+MessageQueueTest.queue.size());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer extends Thread {
	private DB dbInserter;
	private MemoryMQImpl<AbstractMessage> queue;
	
	public Consumer(String name,MemoryMQImpl<AbstractMessage> queue,DB dbInserter) {
		super(name);
		this.queue=queue;
		this.dbInserter=dbInserter;
	}

	@Override
	public void run() {
		//计数器
		int times = 0;
		while (true) {
			AbstractMessage msg = null;
			boolean insertResult=false;
			msg = queue.get();
			 insertResult = dbInserter.save(msg);
			//如果保存失败,就把消息复原
			if(insertResult==false){
				queue.restore(msg);	
			}
			times++;
			if(times%300000==0){
				System.out.println(this.getName()+"已经消费了:"+times+"个Message");
			}
		}
	}
}