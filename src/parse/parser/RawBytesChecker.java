package parse.parser;

import parse.bean.MessageB0;
import parse.bean.MessageB1;
import parse.utils.CodecUtils;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 检测采集上来的原始数据是否正确
 * <p>
 * 本类中有3个check()方法,第一个调用第二个,第二个调用第三个
 */
public class RawBytesChecker {
	/*
	 * 各种错误码
	 */
	public static int WRONG_TOTAL_LENGTH = 1;
	public static int WRONG_68_68_16 = 2;
	public static int WRONG_LENGTH = 3;
	public static int WRONG_CHECK_SUM = 4;
	public static int EXCEPTION_WHEN_PARSE = 100; // 解析时直接报错

	/**
	 * 检验JSONObject中的原始信息
	 * <p>
	 * 它只是调用了下面的check()方法
	 */
	public static int check(JSONObject jsonObj) {
		try {
			JSONObject object1 = jsonObj.getJSONObject("service");
			JSONObject object2 = object1.getJSONObject("data");
			String field1 = object2.getString("field1"); // 用base64编码的原始信息
			return check(field1);
		} catch (Exception e) { // 如果检测过程中报错,也返回错误码,因为谁也不敢保证电信给的json一直都对,弄不好哪里就出了个空指针
			e.printStackTrace();
			return EXCEPTION_WHEN_PARSE;
		}
	}

	/**
	 * 检验base64编码的原始信息
	 * <p>
	 * 它只是调用了下面的check()方法
	 */
	public static int check(String base64Str) {
		byte[] rawbytes = Base64.decode(base64Str);
		return check(rawbytes);
	}

	/**
	 * 这个check()才是真正的检验
	 * <p>
	 * 如果不正确,会返回错误码
	 */
	public static int check(byte[] rawBytes) {
		if (!checkTotalLength(rawBytes)) {
			return WRONG_TOTAL_LENGTH;
		}
		if (!check686816(rawBytes)) {
			return WRONG_68_68_16;
		}
		if (!checkLength(rawBytes)) {
			return WRONG_LENGTH;
		}
		if (!checkCheckSum(rawBytes)) {
			return WRONG_CHECK_SUM;
		}
		return 0; // 如果都没有问题,就通过
	}

	/**
	 * 总长度太小,小于等于8则认为总长度太小
	 */
	private static boolean checkTotalLength(byte[] rawBytes) {
		if (rawBytes == null || rawBytes.length <= 20) {
			return false;
		}
		return true;
	}

	/**
	 * 固定位置上的68,68,16不对
	 */
	private static boolean check686816(byte[] rawBytes) {
		if (rawBytes[0] != 0x68 || rawBytes[5] != 0x68) {
			return false;
		}
		if (rawBytes[rawBytes.length - 1] != 0x16) {
			return false;
		}
		return true;
	}

	/**
	 * 声明的长度和实际长度不相符
	 * <p>
	 * 声明的长度应该比实际长度小8个
	 */
	private static boolean checkLength(byte[] rawBytes) {
		// step1.两个长度值是否相等
		if (rawBytes[1] != rawBytes[3] || rawBytes[2] != rawBytes[4]) {
			return false;
		}
		// step2.声明的长度加上8是否等于总字节长度
		int declaredLength = RawBytesParser.getDeclaredLength(rawBytes);
		if (declaredLength + 8 != rawBytes.length) {
			return false;
		}
		// step3.不同的AFN,长度也是不同的
		// 对于所有类型的数据,都有下面公式: 数据总长度= 20 + 一个消息的长度*n
		byte afn = RawBytesParser.getAFN(rawBytes);
		if (CodecUtils.byteToInt(afn) == 0xB0) { // AFN=B0,实时数据
			if ((rawBytes.length - 20) % MessageB0.MESSAGE_LENGTH != 0) {
				return false;
			}
		} else if (CodecUtils.byteToInt(afn) == 0xB1) { // AFN=B1,冻结数据
			if ((rawBytes.length - 20) % MessageB1.MESSAGE_LENGTH != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验和不对
	 * <p>
	 * 校验和不要前6位和后2位
	 */
	private static boolean checkCheckSum(byte[] rawBytes) {
		byte sum = 0;
		for (int i = 6; i <= rawBytes.length - 3; i++) {
			sum += rawBytes[i];
		}
		if (sum != rawBytes[rawBytes.length - 2]) {
			return false;
		}
		return true;
	}

	/**
	 * 得到正确的校验和
	 * <p>
	 * 这个方法主要是为测试时把自己手写的消息的校验和改对,正式项目中用不上
	 */
	public static byte getCheckSum(byte[] rawBytes) {
		byte sum = 0;
		for (int i = 6; i <= rawBytes.length - 3; i++) {
			sum += rawBytes[i];
		}
		return sum;
	}
}
