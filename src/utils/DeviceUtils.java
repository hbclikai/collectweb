package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
/**
 * 与设备相关的工具方法,都放到这里
 */
public class DeviceUtils {
	public static void main(String[] args) {
		Random random=new Random();
		byte[] address1=new byte[15];
		random.nextBytes(address1);
		byte[] address2={0,0,0,0,0,0,0,1,17,1,0,-20};
		System.out.println(getAddressFromRawBytes(address2));
	}
	
	/**
	 * 从数据库中获取所有设备的id
	 * 这个方法执行得很慢
	 * 当有1.5万个设备的时候,需要300ms才能执行完sql语句
	 * 当有3000个设备的时候,需要100ms才能执行完sql语句
	 */
	public static Set<String> getAllDeviceIDs() {
		QueryRunner runner=new QueryRunner(DaoUtils.getPool());
		//获取所有表名
		String sql="select upper(table_name) from information_schema.tables "
				+ "where table_schema=(select database()) and table_type='base table';";
		List<Object> list;
		Set<String> set=new HashSet<String>(3000);
		try {
			//ColumnListHandler只获取某一列的内容
			list = runner.query(sql, new ColumnListHandler(1));
		} catch (SQLException e) {
			e.printStackTrace();
			return set;
		}	
		for (Object object : list) {
			set.add(object.toString().substring(4)); //去掉表名前面的"div_"
		}
		return set;
	}
	
	/**
	 * 在数据库中生成设备对应的表,表名就是设备id
	 */
	public static void generateDevicesInDB(int count){
		//先随机生成100个设备的id
		List<String> list = DeviceUtils.generateDeviceIds(count);
		for (String id : list) {
			String sql="create table "
					+ "dev_"+id
					+ "(id int primary key auto_increment,"
					+ "elec_amount decimal(16,4),"
					+ "elec_current decimal(16,4),"
					+ "elec_voltage decimal(16,4),"
					+ "v1 decimal(8,2),"
					+ "v2 decimal(8,2),"
					+ "v3 decimal(8,2),"
					+ "v4 decimal(8,2),"
					+ "v5 decimal(8,2),"
					+ "v6 decimal(8,2),"
					+ "v7 decimal(8,2),"
					+ "v8 decimal(8,2),"
					+ "v9 decimal(8,2),"
					+ "v10 decimal(8,2),"
					+ "v11 decimal(8,2),"
					+ "v12 decimal(8,2),"
					+ "v13 decimal(8,2),"
					+ "v14 decimal(8,2),"
					+ "v15 decimal(8,2),"
					+ "v16 decimal(8,2),"
					+ "v17 decimal(8,2),"
					+ "v18 decimal(8,2),"
					+ "v19 decimal(8,2),"
					+ "v20 decimal(8,2),"
					+ "date datetime);";
			DaoUtils.executeSQL(sql);
		}
		System.out.println("生成的表名如下\n"+list);
	}
	
	/**
	 * 随机生成设备的id
	 */
	public static List<String> generateDeviceIds(int count){
		Random random=new Random();
		List<String> ids=new ArrayList<String>();
		for(int i=0;i<count;i++){
			byte[] addr=new byte[15];
			random.nextBytes(addr);
			String strAddr = getAddressFromRawBytes(addr);
			ids.add(strAddr);
		}
		return ids;
	}
	
	/**
	 * 从原始数组中获取地址字符串
	 * 例子:
	 * 	参数:byte[] address={0,0,0,0,0,0,0,1,17,1,0,-20,......};
	 *  结果:01110100EC
	 */
	public static String getAddressFromRawBytes(byte[] raw){
    	if(raw.length<12){
			return "";
		}
    	byte[] addr=new byte[5]; //重新创建一个数组,只要第8个到第12个
    	addr[0]=raw[7];
    	addr[1]=raw[8];
    	addr[2]=raw[9];
    	addr[3]=raw[10];
    	addr[4]=raw[11];
    	return "";	//随便写个
	}
	
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
}
