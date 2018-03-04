package parse.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本类的作用只有一个: 解析376.1协议附录中的各种以A开头的数据的值
 */
public class AUtils {
	/**
	 * 获取数据格式A.5
	 */
	public static double getA5(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 2)) {
			return 0; // 如果是无效数值,就返回0
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte211 = (byte2 >> 7) & 0x01; // S位,0正,1负
		int byte212 = (byte2 >> 4) & 0x07; // 百位只有3个bit的有效内容
		int byte22 = byte2 & 0x0f;
		double result = byte11 + byte12 * 0.1 + byte212 * 100 + byte22 * 10;
		if (byte211 == 1) {
			result *= -1;
		}
		return CodecUtils.toDecimal1(result);
	}

	/**
	 * 获取数据格式A.7
	 */
	public static double getA7(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 2)) {
			return 0; // 如果是无效数值,就返回0
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		double result = byte11 + byte12 * 0.1 + byte21 * 100 + byte22 * 10;
		return CodecUtils.toDecimal1(result);
	}

	/**
	 * 获取数据格式A.9
	 */
	public static double getA9(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 3)) {
			return 0; // 如果是无效数值,就返回0
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		byte byte3 = rawBytes[position + 2];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		int byte311 = (byte3 >> 7) & 0x01; // S位,0正,1负
		int byte312 = (byte3 >> 4) & 0x07; // 百位只有3个bit的有效内容
		int byte32 = byte3 & 0x0f;
		double result = byte11 * 0.001 + byte12 * 0.0001 + byte21 * 0.1
				+ byte22 * 0.01 + byte312 * 10 + byte32;
		if (byte311 == 1) {
			result *= -1;
		}
		return CodecUtils.toDecimal4(result);
	}

	/**
	 * 获取数据格式A.11
	 */
	public static double getA11(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 4)) {
			return 0; // 如果是无效数值,就返回0
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		byte byte3 = rawBytes[position + 2];
		byte byte4 = rawBytes[position + 3];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		int byte31 = (byte3 >> 4) & 0x0f;
		int byte32 = byte3 & 0x0f;
		int byte41 = (byte4 >> 4) & 0x0f;
		int byte42 = byte4 & 0x0f;
		double result = byte11 * 0.1 + byte12 * 0.01 + byte21 * 10 + byte22
				+ byte31 * 1000 + byte32 * 100 + byte41 * 100000 + byte42
				* 10000;
		return CodecUtils.toDecimal2(result);
	}

	/**
	 * 获取数据格式A.14
	 */
	public static double getA14(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 5)) {
			return 0; // 如果是无效数值,就返回0
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		byte byte3 = rawBytes[position + 2];
		byte byte4 = rawBytes[position + 3];
		byte byte5 = rawBytes[position + 4];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		int byte31 = (byte3 >> 4) & 0x0f;
		int byte32 = byte3 & 0x0f;
		int byte41 = (byte4 >> 4) & 0x0f;
		int byte42 = byte4 & 0x0f;
		int byte51 = (byte5 >> 4) & 0x0f;
		int byte52 = byte5 & 0x0f;
		double result = byte11 * 0.001 + byte12 * 0.0001 + byte21 * 0.1
				+ byte22 * 0.01 + byte31 * 10 + byte32 + byte41 * 1000 + byte42
				* 100 + byte51 * 100000 + byte52 * 10000;
		return CodecUtils.toDecimal4(result);
	}

	/**
	 * 获取数据格式A.15(时间5字节)
	 */
	public static Date getA15(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 5)) {
			return null; // 如果是无效数值,就返回null
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		byte byte3 = rawBytes[position + 2];
		byte byte4 = rawBytes[position + 3];
		byte byte5 = rawBytes[position + 4];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		int byte31 = (byte3 >> 4) & 0x0f;
		int byte32 = byte3 & 0x0f;
		int byte41 = (byte4 >> 4) & 0x0f;
		int byte42 = byte4 & 0x0f;
		int byte51 = (byte5 >> 4) & 0x0f;
		int byte52 = byte5 & 0x0f;
		// 自己用数来拼,大约比用StringBuilder快20%,内存占用也少得多
		long ltime = 20000000000000L
				+ // 固定是20xx年
				byte51 * 100000000000L + byte52 * 10000000000L + byte41
				* 1000000000L + byte42 * 100000000 + byte31 * 10000000 + byte32
				* 1000000 + byte21 * 100000 + byte22 * 10000 + byte11 * 1000
				+ byte12 * 100;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(String
					.valueOf(ltime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取数据格式A.17(时间4字节)
	 * <p>
	 * A17中没有年,需要传入一个年,这个年应该是A15中的年
	 * <p>
	 * 之所以这么设计,就是因为如果使用系统的年,可能在跨年的那几秒会产生偏差一年的数据
	 * <p>
	 * 所以,从A15中取出年份值,就能保证时间的一致性
	 */
	public static Date getA17(byte[] rawBytes, int position, int year) {
		if (isEE(rawBytes, position, 4)) {
			return null; // 如果是无效数值,就返回null
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		byte byte3 = rawBytes[position + 2];
		byte byte4 = rawBytes[position + 3];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		int byte31 = (byte3 >> 4) & 0x0f;
		int byte32 = byte3 & 0x0f;
		int byte41 = (byte4 >> 4) & 0x0f;
		int byte42 = byte4 & 0x0f;
		// 自己用数来拼,大约比用StringBuilder快20%,内存占用也少得多
		long ltime = year * 10000000000L + byte41 * 1000000000L + byte42
				* 100000000 + byte31 * 10000000 + byte32 * 1000000 + byte21
				* 100000 + byte22 * 10000 + byte11 * 1000 + byte12 * 100;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(String
					.valueOf(ltime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取数据格式A.20
	 */
	public static Date getA20(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 3)) {
			return null; // 如果是无效数值,就返回null
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		byte byte3 = rawBytes[position + 2];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		int byte31 = (byte3 >> 4) & 0x0f;
		int byte32 = byte3 & 0x0f;
		// 自己用数来拼,大约比用StringBuilder快20%,内存占用也少得多
		long ltime = 20000000000000L
				+ // 固定是20xx年
				byte31 * 100000000000L + byte32 * 10000000000L + byte21
				* 1000000000L + byte22 * 100000000 + byte11 * 10000000 + byte12
				* 1000000;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(String
					.valueOf(ltime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取数据格式A.23
	 */
	public static double getA23(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 3)) {
			return 0; // 如果是无效数值,就返回0
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		byte byte3 = rawBytes[position + 2];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		int byte31 = (byte3 >> 4) & 0x0f;
		int byte32 = byte3 & 0x0f;
		double result = byte11 * 0.001 + byte12 * 0.0001 + byte21 * 0.1
				+ byte22 * 0.01 + byte31 * 10 + byte32;
		return CodecUtils.toDecimal4(result);
	}

	/**
	 * 获取数据格式A.25
	 */
	public static Double getA25(byte[] rawBytes, int position) {
		if (isEE(rawBytes, position, 3)) {
			return null; // 如果是无效数值,就返回null
		}
		byte byte1 = rawBytes[position];
		byte byte2 = rawBytes[position + 1];
		byte byte3 = rawBytes[position + 2];
		int byte11 = (byte1 >> 4) & 0x0f;
		int byte12 = byte1 & 0x0f;
		int byte21 = (byte2 >> 4) & 0x0f;
		int byte22 = byte2 & 0x0f;
		int byte311 = (byte3 >> 7) & 0x01; // S位,0正,1负
		int byte312 = (byte3 >> 4) & 0x07; // 百位只有3个bit的有效内容
		int byte32 = byte3 & 0x0f;
		double result = byte11 * 0.01 + byte12 * 0.001 + byte21 + byte22 * 0.1
				+ byte312 * 100 + byte32 * 10;
		if (byte311 == 1) {
			result *= -1;
		}
		return CodecUtils.toDecimal3(result);
	}

	/**
	 * 检查一下是不是EEEE这种无效的内容
	 */
	public static boolean isEE(byte[] rawBytes, int position, int count) {
		for (int i = 0; i < count; i++) {
			if (rawBytes[position + i] != -18) { // EE是-18,注意0xEE是int型的正数,而EE是负数,所以只能写-18,不能写0xEE
				return false;
			}
		}
		return true;
	}

}
