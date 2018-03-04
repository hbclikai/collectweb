package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

import utils.redisutils.JedisUtils;

public class DaoUtils {
	private static BasicDataSource pool = new BasicDataSource();

	static {
		InputStream is = JedisUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties pp=new Properties();
		try {
			pp.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String driver=pp.getProperty("mysql.driver");
		String url=pp.getProperty("mysql.url");
		String username=pp.getProperty("mysql.username");
		String password=pp.getProperty("mysql.password");
		pool.setDriverClassName(driver);
		pool.setUrl(url);
		pool.setUsername(username);
		pool.setPassword(password);
	}

	public static Connection getConnection() {
		Connection con = null;
		try {
			con = pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static BasicDataSource getPool(){
		return pool;
	}

	/**
	 * 执行sql语句
	 */
	public static int executeSQL(String sql) {
	    Connection conn = DaoUtils.getConnection();
	    PreparedStatement pstmt;
	    int i=0;
	    try {
	        pstmt =  conn.prepareStatement(sql);
	        //pstmt.setString(1, "abc");
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
}
