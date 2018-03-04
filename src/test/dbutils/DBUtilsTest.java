package test.dbutils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Before;
import org.junit.Test;


public class DBUtilsTest {
	/*
	 * 要想使用dbutils里面的类,必须要有一个连接池,所以我们先
	 * 创建一个DBCP连接池
	 */
	BasicDataSource pool=null;
	
	/*
	 * 测试之前,先初始化连接池
	 */
	@Before
	public void before(){
		//创建一个连接池
		pool=new BasicDataSource();
		pool.setDriverClassName("com.mysql.jdbc.Driver");	//这4个set是必不可少的
		pool.setUrl("jdbc:mysql://localhost:3306/db4");
		pool.setUsername("root");		
		pool.setPassword("123456");
	}
	
	/*
	 * 增加
	 */
	@Test
	public void testAdd() throws SQLException {
		//1dbutils中的核心类,负责执行sql语句
		QueryRunner runner=new QueryRunner(pool);//给它一个连接池就可以了
		//2编写sql语句
		String sql="insert into guyuan(id,name,ruzhishijian,gongzi) values(?,?,?,?)";
		//3为上面的占位符(?)设置值
		Object[] canshu= {9530,"lucy",new Date(),1234.56};
		//4可以执行sql语句了
		runner.update(sql,canshu);
		//5不用关闭资源,它会自动关闭
	}
	
	/*
	 * 删除
	 */
	@Test
	public void testDelete() throws SQLException {
		//1dbutils中的核心类,负责执行sql语句
		QueryRunner runner=new QueryRunner(pool);
		//2编写sql语句
		String sql="delete from guyuan where id=9527";
		//3可以执行sql语句了
		int n = runner.update(sql);	//增删改用的都是update
		System.out.println(n);
	}
	
	/*
	 * 修改
	 */
	@Test
	public void testUpdate() throws SQLException {
		//1dbutils中的核心类,负责执行sql语句
		QueryRunner runner=new QueryRunner(pool);
		//2编写sql语句
		String sql="update guyuan set name=? where id=?";
		//3为上面的占位符(?)设置值
		Object[] canshu= {"lily",123};
		//4可以执行sql语句了
		int n = runner.update(sql,canshu);	//增删改用的都是update
		System.out.println(n);
	}
	/**
	 *直接获得一个javabean
	 */
	@Test
	public void testGetOneGuyuan() throws SQLException {
		//1dbutils中的核心类,负责执行sql语句
		QueryRunner runner=new QueryRunner(pool);
		//2编写sql语句
		String sql="select * from guyuan where id=158";
		//3执行查询
		//BeanHandler的意思是, 专门操作javabean的一个工具类
		BeanHandler<Guyuan> handler = new BeanHandler<Guyuan>(Guyuan.class);
		Guyuan g=runner.query(sql,handler );
		System.out.println(g);
	}
	
	/**
	 *直接获得一个javabean(带参数的)
	 */
	@Test
	public void testGetOneGuyuan1() throws SQLException {
		//1dbutils中的核心类,负责执行sql语句
		QueryRunner runner=new QueryRunner(pool);
		//2编写sql语句
		String sql="select * from guyuan where id=?";
		//3为上面的占位符(?)设置值
		Object[] canshu= {213};
		//4执行查询
		Guyuan g=runner.query(sql, new BeanHandler<Guyuan>(Guyuan.class),canshu);
		System.out.println(g);
	}
	
	/**
	 *直接获得一个List<javabean>
	 */
	@Test
	public void testGetAllGuyuan() throws SQLException {
		//1dbutils中的核心类,负责执行sql语句
		QueryRunner runner=new QueryRunner(pool);
		//2编写sql语句
		String sql="select * from guyuan";
		List<Guyuan> list = runner.query(sql, new BeanListHandler<Guyuan>(Guyuan.class));
		for (Guyuan guyuan : list) {
			System.out.println(guyuan);
		}
	}
	
	/**
	 * 查询个数
	 */
	@Test
	public void testGetGeshu() throws SQLException {
		//1dbutils中的核心类,负责执行sql语句
		QueryRunner runner=new QueryRunner(pool);
		//2编写sql语句
		String sql="select count(*) from guyuan";
		Long n=(Long)runner.query(sql, new ScalarHandler());//这个方法返回一个Object, 需要把它转成Long型
		System.out.println(n);
	}
	
	/**
	 * 只要一列
	 */
	@Test
	public void testOneColumn() throws SQLException {
		//1dbutils中的核心类,负责执行sql语句
		QueryRunner runner=new QueryRunner(pool);
		//2编写sql语句
		String sql="select * from guyuan";
		List<Object> list = runner.query(sql, new ColumnListHandler("name"));//要那一列,这里就写哪一列
		for (Object o : list) {
			System.out.println(o);
		}
	}
}
