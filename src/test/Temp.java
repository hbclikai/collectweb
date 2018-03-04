package test;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

import utils.DaoUtils;




public class Temp {
	public static void main(String[] args) throws Exception {
		//0 根据设备id在数据库中创建表
		//DeviceUtils.generateDevicesInDB(3000);
		
		//1 读取所有正确的id
//		Set<String> set = DeviceUtils.getAllDeviceIDs();
//		System.out.println(set);
		
		
		//2 fastjson测试
//		Message msg=new Message();
//		msg.makeFakeData();
//		String json = JSON.toJSONString(msg);
//		System.out.println(json);
//		Message obj = JSON.parseObject(json,Message.class);
//		System.out.println(obj.getId());
		
		//3
//        QueryRunner qr = new QueryRunner(DaoUtils.getPool());  
//        String sql = "insert into ?(name, money) values(?,?)";  
//        Object[][] param= new Object[10][];  
//        for(int i=0; i<10; i++){  
//            param[i]= new Object[2];  
//            param[i][0] = "name"+i*10+0;  
//            param[i][1] = i*10+1;  
//        }  
//        qr.batch(sql, param);
        
        //4 dbutils批量事务处理
        Connection conn = null;
        try{
            conn = DaoUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            /**
             * 在创建QueryRunner对象时，不传递数据源给它，是为了保证这两条SQL在同一个事务中进行，
             * 我们手动获取数据库连接，然后让这两条SQL使用同一个数据库连接执行
             */
            QueryRunner runner = new QueryRunner();
            String sql1 = "insert into dev_000B5CB7E4(elec_amount,elec_current,elec_voltage,date) values(?,?,?,now())";
            String sql2 = "insert into dev_00A6D47658(elec_amount,elec_current,elec_voltage,date) values(?,?,?,now())";
            Object[] param1 = {11.1,22.2,33.3};
            Object[] param2 = {11.1,22.2,33.3};
            runner.update(conn,sql1,param1);
            int i=1/0;
            runner.update(conn,sql2,param2);
            //sql正常执行之后就提交事务
            conn.commit();
        }catch (Exception e) {
            e.printStackTrace();
            if(conn!=null){
                //出现异常之后就回滚事务
                conn.rollback();
            }
        }finally{
            //关闭数据库连接
            conn.close();
        }
        
        
	}
}
