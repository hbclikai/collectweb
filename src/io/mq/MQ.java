package io.mq;

import java.util.List;

/**
 * 外部消息队列,是第2级缓存
 */
public interface MQ<T> {
	public boolean put(String topicName, T t);
	/**
	 * 一次取出一条
	 */
	public T get(String topicName);
	
	/**
	 * 一次取出多条,count是要取出的条数 
	 */
	public List<T> get(String topicName,int count);

	public long size(String topicName);

	/**
	 * 如果取走这条消息后,无法插入到数据库或者kafka,就恢复这条消息
	 */
	public void restore(String topicName, T t);

}
