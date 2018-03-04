package parse.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
/**
 * 进制的工具类
 * 本类不和业务发生任何关系
 */
public class CodecUtils {
	/**
	 * 调用commons-codec中的方法 因为自己写的转换代码效率比commons中的低得多
	 */
	public static String bytesToHexString(byte[] bytes) {
		return Hex.encodeHexString(bytes, false);
	}

	/**
	 * 调用commons-codec中的方法
	 */
	public static byte[] hexStringToBytes(String hexString) {
		byte[] bytes = null;
		try {
			bytes = Hex.decodeHex(hexString.toCharArray());
			return bytes;
		} catch (DecoderException e) {
		}
		return new byte[0];
	}

	/**
	 * 这个只是在上边的基础上,去掉了目标字符串中的空格 适用于下面这样的: 68 a2 00 a2 00 68 46 82
	 */
	public static byte[] hexStringWithBlankToBytes(String hexString) {
		String newHexString = hexString.replace(" ", "");
		return hexStringToBytes(newHexString);
	}

	/**
	 * 获得一个整数某位上的值 可以用于int和byte
	 * <p>按下面顺序获取
	 * <p>D7 D6 D5 D4 D3 D2 D1 D0
	 * <p>例如: 获取0X23的D1, 就用CodecUtils.getBitOfInt(0X23,1)
	 */
	public static int getBitOfInt(int target, int position) {
		return (target >> position) & 0x1;
	}
	
	/**
	 * 将byte转换成int型
	 * <p>注意:转换后的结果都是正的
	 * <p>byte有负值,但转成int后都是正数,也就是把byte的符号位当成数值来计算了
	 * <p>例:byte	int
	 * <p>	1	=>	1
	 * <p>	127	=>	127
	 * <p>	-1	=>	255
	 * <p>	-128=>	128
	 */
	public static int byteToInt(byte b){
		return 0xFF & b;
	}
	
	/**
	 * double型保留1位小数
	 */
	public static double toDecimal1(double target){
		return Math.round(target*10)/10.0;
	}
	
	/**
	 * double型保留2位小数
	 */
	public static double toDecimal2(double target){
		return Math.round(target*100)/100.0;
	}
	
	/**
	 * double型保留3位小数
	 */
	public static double toDecimal3(double target){
		return Math.round(target*1000)/1000.0;
	}
	
	/**
	 * double型保留4位小数
	 */
	public static double toDecimal4(double target){
		return Math.round(target*10000)/10000.0;
	}
	
	/**
	 * 把电信NB平台的时间格式转换成long格式
	 * <p>
	 * 如:20180208T075436Z
	 * <p>
	 * 这里有个时区的问题,电信的时区是格林威治标准时区,需要加上8个小时才是中国的时区
	 */
	public static Date getNBPlatformTime(String strTime) {
		Date date = null;
		try {
			date = new SimpleDateFormat(
					"yyyyMMdd'T'HHmmss'Z'").parse(strTime);	//电信专用的日期格式
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 8); // 加上8个小时
		return calendar.getTime();
	}
}
