package io.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import parse.bean.AbstractMessage;

public class HbaseDedmo {

	// 与HBase数据库的连接对象
	private static Connection connection;

	// 数据库元数据操作对象
	private static Admin admin;

	static {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum","192.168.1.66:2181,192.168.1.60:2181,192.168.1.65:2181");
		conf.set("hbase.master", "192.168.1.66:60000");
		
		try {
			// 取得一个数据库连接对象
			connection = ConnectionFactory.createConnection(conf);
			admin = connection.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		insert();
	}
	
	/**
	 * 插入数据
	 */
	public static void insert() throws IOException {
		Table table = connection.getTable(TableName.valueOf("collect"));
		String rowKey = "key"+System.currentTimeMillis();	//rowKey
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("f101"),Bytes.toBytes(1));
		put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("f101"),Bytes.toBytes(2));
		table.put(put);
	}
	



	/**
	 * 创建表
	 */
	public void createTable() throws IOException {

		System.out.println("---------------创建表 START-----------------");

		// 数据表表名
		String tableNameString = "t_book";

		// 新建一个数据表表名对象
		TableName tableName = TableName.valueOf(tableNameString);

		// 如果需要新建的表已经存在
		if (admin.tableExists(tableName)) {

			System.out.println("表已经存在！");
		}
		// 如果需要新建的表不存在
		else {

			// 数据表描述对象
			HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);

			// 列族描述对象
			HColumnDescriptor family = new HColumnDescriptor("base");
			;

			// 在数据表中新建一个列族
			hTableDescriptor.addFamily(family);

			// 新建数据表
			admin.createTable(hTableDescriptor);
		}

		System.out.println("---------------创建表 END-----------------");
	}

	/**
	 * 查询整表数据
	 */

	public void queryTable() throws IOException {

		System.out.println("---------------查询整表数据 START-----------------");

		// 取得数据表对象
		Table table = connection.getTable(TableName.valueOf("t_book"));

		// 取得表中所有数据
		ResultScanner scanner = table.getScanner(new Scan());

		// 循环输出表中的数据
		for (Result result : scanner) {

			byte[] row = result.getRow();
			System.out.println("row key is:" + new String(row));

			List<Cell> listCells = result.listCells();
			for (Cell cell : listCells) {

				byte[] familyArray = cell.getFamilyArray();
				byte[] qualifierArray = cell.getQualifierArray();
				byte[] valueArray = cell.getValueArray();

				System.out.println("row value is:" + new String(familyArray)
						+ new String(qualifierArray) + new String(valueArray));
			}
		}

		System.out.println("---------------查询整表数据 END-----------------");

	}

	/**
	 * 按行键查询表数据
	 */

	public void queryTableByRowKey() throws IOException {

		System.out.println("---------------按行键查询表数据 START-----------------");

		// 取得数据表对象
		Table table = connection.getTable(TableName.valueOf("t_book"));

		// 新建一个查询对象作为查询条件
		Get get = new Get("row8".getBytes());

		// 按行键查询数据
		Result result = table.get(get);

		byte[] row = result.getRow();
		System.out.println("row key is:" + new String(row));

		List<Cell> listCells = result.listCells();
		for (Cell cell : listCells) {

			byte[] familyArray = cell.getFamilyArray();
			byte[] qualifierArray = cell.getQualifierArray();
			byte[] valueArray = cell.getValueArray();

			System.out.println("row value is:" + new String(familyArray)
					+ new String(qualifierArray) + new String(valueArray));
		}

		System.out.println("---------------按行键查询表数据 END-----------------");

	}

	/**
	 * 按条件查询表数据
	 */

	public void queryTableByCondition() throws IOException {

		System.out.println("---------------按条件查询表数据 START-----------------");

		// 取得数据表对象
		Table table = connection.getTable(TableName.valueOf("t_book"));

		// 创建一个查询过滤器
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes("base"),
				Bytes.toBytes("name"), CompareOp.EQUAL,
				Bytes.toBytes("bookName6"));

		// 创建一个数据表扫描器
		Scan scan = new Scan();

		// 将查询过滤器加入到数据表扫描器对象
		scan.setFilter(filter);

		// 执行查询操作，并取得查询结果
		ResultScanner scanner = table.getScanner(scan);

		// 循环输出查询结果
		for (Result result : scanner) {
			byte[] row = result.getRow();
			System.out.println("row key is:" + new String(row));

			List<Cell> listCells = result.listCells();
			for (Cell cell : listCells) {

				byte[] familyArray = cell.getFamilyArray();
				byte[] qualifierArray = cell.getQualifierArray();
				byte[] valueArray = cell.getValueArray();

				System.out.println("row value is:" + new String(familyArray)
						+ new String(qualifierArray) + new String(valueArray));
			}
		}

		System.out.println("---------------按条件查询表数据 END-----------------");

	}

	/**
	 * 清空表
	 */

	public void truncateTable() throws IOException {

		System.out.println("---------------清空表 START-----------------");

		// 取得目标数据表的表名对象
		TableName tableName = TableName.valueOf("t_book");

		// 设置表状态为无效
		admin.disableTable(tableName);
		// 清空指定表的数据
		admin.truncateTable(tableName, true);

		System.out.println("---------------清空表 End-----------------");
	}

	/**
	 * 删除表
	 */

	public void deleteTable() throws IOException {

		System.out.println("---------------删除表 START-----------------");

		// 设置表状态为无效
		admin.disableTable(TableName.valueOf("t_book"));
		// 删除指定的数据表
		admin.deleteTable(TableName.valueOf("t_book"));

		System.out.println("---------------删除表 End-----------------");
	}

	/**
	 * 删除行
	 */

	public void deleteByRowKey() throws IOException {

		System.out.println("---------------删除行 START-----------------");

		// 取得待操作的数据表对象
		Table table = connection.getTable(TableName.valueOf("t_book"));

		// 创建删除条件对象
		Delete delete = new Delete(Bytes.toBytes("row2"));

		// 执行删除操作
		table.delete(delete);

		System.out.println("---------------删除行 End-----------------");

	}

	/**
	 * 删除行（按条件）
	 */
	public void deleteByCondition() throws IOException,
			DeserializationException {

		System.out.println("---------------删除行（按条件） START-----------------");

		// 步骤1：调用queryTableByCondition()方法取得需要删除的数据列表

		// 步骤2：循环步骤1的查询结果，对每个结果调用deleteByRowKey()方法

		System.out.println("---------------删除行（按条件） End-----------------");

	}

	/**
	 * 新建列族
	 */
	public void addColumnFamily() throws IOException {

		System.out.println("---------------新建列族 START-----------------");

		// 取得目标数据表的表名对象
		TableName tableName = TableName.valueOf("t_book");

		// 创建列族对象
		HColumnDescriptor columnDescriptor = new HColumnDescriptor("more");

		// 将新创建的列族添加到指定的数据表
		admin.addColumn(tableName, columnDescriptor);

		System.out.println("---------------新建列族 END-----------------");
	}

	/**
	 * 删除列族
	 */
	public void deleteColumnFamily() throws IOException {

		System.out.println("---------------删除列族 START-----------------");

		// 取得目标数据表的表名对象
		TableName tableName = TableName.valueOf("t_book");

		// 删除指定数据表中的指定列族
		admin.deleteColumn(tableName, "more".getBytes());

		System.out.println("---------------删除列族 END-----------------");
	}



}