package io.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import parse.bean.AbstractMessage;

public class DBHbaseImpl implements DB<AbstractMessage>{
	// 与HBase数据库的连接对象
	private static Connection connection;

	// 数据库元数据操作对象
	// private static Admin admin;

	static {
		Configuration conf = HBaseConfiguration.create();
//		conf.set("hbase.zookeeper.quorum","192.168.1.62:2181,192.168.1.60:2181,192.168.1.61:2181");
//		conf.set("hbase.master", "192.168.1.61:60000");
		conf.set("hbase.zookeeper.quorum", "192.168.159.170"); // 这个是3台机器的
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		try {
			// 取得一个数据库连接对象
			connection = ConnectionFactory.createConnection(conf);
			//admin = connection.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean save(AbstractMessage msg) {
		Table table;
		try {
			table = connection.getTable(TableName.valueOf("collect"));
		} catch (IOException e) {
			System.out.println("getTable时发生错误");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
			return false;
		}
		Random r=new Random();
		String rowKey = "key" + System.currentTimeMillis();	//rowKey
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("f101"),Bytes.toBytes(r.nextDouble()));
		put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("f102"),Bytes.toBytes(r.nextDouble()));
		put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("f103"),Bytes.toBytes(r.nextDouble()));
			
		try {
			table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean save(List<AbstractMessage> list) {
		Table table;
		try {
			table = connection.getTable(TableName.valueOf("collect"));
		} catch (IOException e) {
			System.out.println("getTable时发生错误");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
			return false;
		}
		List<Put> putList = new ArrayList<Put>();
		Random r=new Random();
		for (AbstractMessage msg : list) {
			String rowKey = "key" + System.currentTimeMillis();	//rowKey
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("f101"),Bytes.toBytes(r.nextDouble()));
			put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("f102"),Bytes.toBytes(r.nextDouble()));
			put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("f103"),Bytes.toBytes(r.nextDouble()));

			putList.add(put);
		}
		try {
			table.put(putList);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
