package io.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import parse.bean.AbstractMessage;
import utils.DaoUtils;

public class DBMysqlImpl implements DB<AbstractMessage> {
	@Override
	public boolean save(AbstractMessage msg) {
		return true;
	}

	@Override
	public boolean save(List<AbstractMessage> msgs) {
		boolean saveResult=false;	//假设保存结果为失败
		
        Connection conn = null;
        try{
            conn = DaoUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            /**
             * 在创建QueryRunner对象时，不传递数据源给它，是为了保证这两条SQL在同一个事务中进行，
             */
            QueryRunner runner = new QueryRunner();
            for (AbstractMessage msg : msgs) {
            	StringBuilder sb=new StringBuilder(200);
				sb.append("insert into dev_");
//				sb.append(msg.getId());
//				sb.append("(elec_amount,elec_current,elec_voltage,v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,date) values(");
//				sb.append(msg.getElec_amount());
//				sb.append(",");
//				sb.append(msg.getElec_current());
//				sb.append(",");
//				sb.append(msg.getElec_voltage());
//				sb.append(",");sb.append(msg.getV1());
//				sb.append(",");sb.append(msg.getV2());
//				sb.append(",");sb.append(msg.getV3());
//				sb.append(",");sb.append(msg.getV4());
//				sb.append(",");sb.append(msg.getV5());
//				sb.append(",");sb.append(msg.getV6());
//				sb.append(",");sb.append(msg.getV7());
//				sb.append(",");sb.append(msg.getV8());
//				sb.append(",");sb.append(msg.getV9());
//				sb.append(",");sb.append(msg.getV10());
//				sb.append(",");sb.append(msg.getV11());
//				sb.append(",");sb.append(msg.getV12());
//				sb.append(",");sb.append(msg.getV13());
//				sb.append(",");sb.append(msg.getV14());
//				sb.append(",");sb.append(msg.getV15());
//				sb.append(",");sb.append(msg.getV16());
//				sb.append(",");sb.append(msg.getV17());
//				sb.append(",");sb.append(msg.getV18());
//				sb.append(",");sb.append(msg.getV19());
//				sb.append(",");sb.append(msg.getV20());
				sb.append(",now())");
				//Object[] param = {};
				runner.update(conn,sb.toString());
			}
            
//            String sql2 = "insert into dev_00A6D47658(elec_amount,elec_current,elec_voltage,date) values(?,?,?,now())";
//            Object[] param1 = {11.1,22.2,33.3};
//            Object[] param2 = {11.1,22.2,33.3};
//            int i=1/0;
//            runner.update(conn,sql2,param2);
            //sql正常执行之后就提交事务
            conn.commit();
            saveResult=true;
        }catch (Exception e) {	//出现异常之后就回滚事务
            e.printStackTrace();
            if(conn!=null){
                try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }finally{	//关闭数据库连接
            try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }  
        return saveResult;
	}
}
