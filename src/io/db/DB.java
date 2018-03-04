package io.db;

import java.util.List;

public interface DB<T> {
	/**
	 * 向数据库插入数据,插入成功则返回true,失败返回false
	 */
	public boolean save(T t);
	/**
	 * 批量插入
	 */
	public boolean save(List<T> ts);

}
