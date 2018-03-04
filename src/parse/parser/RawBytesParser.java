package parse.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import parse.bean.AbstractMessage;
import parse.bean.MessageB0;
import parse.bean.MessageB1;
import parse.utils.AUtils;
import parse.utils.CodecUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class RawBytesParser {
	// -------------------------------------------第1部分: 给外部使用的方法-----------------------------------------------------
	/**
	 * 从base64字符串读取出Message(生产环境使用)
	 * <p>
	 * 例:传入这样的:"ElFBQEAwxY="
	 */
	public static List<AbstractMessage> readMessagesFromBase64Str(
			String base64Str) {
		byte[] rawbytes = Base64.decode(base64Str);
		return readMessagesFromBytes(rawbytes); // 调用readMsgFromBytes()方法
	}

	/**
	 * 从hex字符串读取出Message(开发环境使用)
	 * <p>
	 * 例:传入这样的:"68 76 06 76 06 68 CA 00 00"(有没有空格都行)
	 */
	public static List<AbstractMessage> readMessagesFromHexStr(String hexStr) {
		byte[] rawBytes = CodecUtils.hexStringWithBlankToBytes(hexStr);
		return readMessagesFromBytes(rawBytes); // 调用readMsgFromBytes()方法
	}

	/**
	 * 上面两个方法都调用本方法,本方法才是真正的解析
	 * <p>
	 * 本方法会根据不同的AFN,调用相应的方法来解析
	 */
	private static List<AbstractMessage> readMessagesFromBytes(byte[] rawBytes) {
		int AFN = CodecUtils.byteToInt(getAFN(rawBytes)); // 获取应用层功能码AFN,要转成int型,否则有负值
		if (AFN == 0xB0) { // AFN=B0
			return readB0s(rawBytes);
		} else if (AFN == 0xB1) { // AFN=B1
			return readB1s(rawBytes);
		}
		return null;
	}

	
	// -------------------------------------------第2部分: 解析具体某个AFN,变成一个小消息-----------------------------------------------------
	/**
	 * 解析AFN=B0的消息
	 */
	private static List<AbstractMessage> readB0s(byte[] rawBytes) {
		List<AbstractMessage> list = new ArrayList<>();
		int position = 18; // 起始位置

		// 读id
		String addrStr = getAddrString(rawBytes);

		// 反复读数据内容
		while (true) { // 有多少读多少
			MessageB0 msg = readOneB0Message(rawBytes, position);
			if (msg == null) { // 如果没有了,就停止
				break;
			}
			msg.setId(addrStr); // 设置id
			position += MessageB0.MESSAGE_LENGTH; // 别忘了移动位置指针
			list.add(msg); // 添加到list中
		}

		// 返回结果
		return list;
	}

	/**
	 * 读取B0中的一个小消息
	 */
	private static MessageB0 readOneB0Message(byte[] rawBytes, int position) {
		if (rawBytes.length == position + 2) { // 读到最后,没有消息了
			return null;
		}
		int pos = position; // 用本地变量保存position,因为直接在参数position上操作,不会有任何效果
		MessageB0 msg = new MessageB0();
		// 本条消息的采集时间
		Date date = AUtils.getA15(rawBytes, pos);
		msg.setcTime(date);
		pos += 5;

		// 2:A9:r2-r9
		msg.setR2(AUtils.getA9(rawBytes, pos));
		pos += 3;
		msg.setR3(AUtils.getA9(rawBytes, pos));
		pos += 3;
		msg.setR4(AUtils.getA9(rawBytes, pos));
		pos += 3;
		msg.setR5(AUtils.getA9(rawBytes, pos));
		pos += 3;
		msg.setR6(AUtils.getA9(rawBytes, pos));
		pos += 3;
		msg.setR7(AUtils.getA9(rawBytes, pos));
		pos += 3;
		msg.setR8(AUtils.getA9(rawBytes, pos));
		pos += 3;
		msg.setR9(AUtils.getA9(rawBytes, pos));
		pos += 3;

		// 3:A5:r10-r13(还要除以100,因为功率因数是xx%,我的结果是0.12这样的,代表12%)
		// 别的都不用toDecimal,但是,这个除以100了,就需要去小数点了
		msg.setR10(CodecUtils.toDecimal3(AUtils.getA5(rawBytes, pos) / 100));
		pos += 2;
		msg.setR11(CodecUtils.toDecimal3(AUtils.getA5(rawBytes, pos) / 100));
		pos += 2;
		msg.setR12(CodecUtils.toDecimal3(AUtils.getA5(rawBytes, pos) / 100));
		pos += 2;
		msg.setR13(CodecUtils.toDecimal3(AUtils.getA5(rawBytes, pos) / 100));
		pos += 2;

		// 4:A7:r14-r16
		msg.setR14(AUtils.getA7(rawBytes, pos));
		pos += 2;
		msg.setR15(AUtils.getA7(rawBytes, pos));
		pos += 2;
		msg.setR16(AUtils.getA7(rawBytes, pos));
		pos += 2;

		// 5:A25:r17-r19
		msg.setR17(AUtils.getA25(rawBytes, pos));
		pos += 3;
		msg.setR18(AUtils.getA25(rawBytes, pos));
		pos += 3;
		msg.setR19(AUtils.getA25(rawBytes, pos));
		pos += 3;

		// 6:A5:r20-r25
		msg.setR20(AUtils.getA5(rawBytes, pos));
		pos += 2;
		msg.setR21(AUtils.getA5(rawBytes, pos));
		pos += 2;
		msg.setR22(AUtils.getA5(rawBytes, pos));
		pos += 2;
		msg.setR23(AUtils.getA5(rawBytes, pos));
		pos += 2;
		msg.setR24(AUtils.getA5(rawBytes, pos));
		pos += 2;
		msg.setR25(AUtils.getA5(rawBytes, pos));
		pos += 2;

		// 7:A14:r26-r27
		msg.setR26(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setR27(AUtils.getA14(rawBytes, pos));
		pos += 5;

		return msg;
	}

	/**
	 * 解析AFN=B1的消息
	 * <p>
	 * 虽然AFN=B1的里面只包含一个小消息,但是还是返回了List,因为指不定什么时候就改成多个小消息了
	 */
	private static List<AbstractMessage> readB1s(byte[] rawBytes) {
		List<AbstractMessage> list = new ArrayList<>();
		int position = 18; // 起始位置

		// 读id
		String addrStr = getAddrString(rawBytes);

		// 反复读数据内容
		while (true) { // 有多少读多少
			MessageB1 msg = readOneB1Message(rawBytes, position);
			if (msg == null) { // 如果没有了,就停止
				break;
			}
			msg.setId(addrStr); // 设置id
			position += MessageB1.MESSAGE_LENGTH; // 别忘了移动位置指针
			list.add(msg); // 添加到list中
		}

		// 返回结果
		return list;
	}

	/**
	 * 读取B1中的一个小消息
	 */
	@SuppressWarnings("deprecation")
	private static MessageB1 readOneB1Message(byte[] rawBytes, int position) {
		if (rawBytes.length == position + 2) { // 读到最后,没有消息了
			return null;
		}
		int pos = position; // 用本地变量保存position,因为直接在参数position上操作,不会有任何效果
		MessageB1 msg = new MessageB1();
		// 本条消息的采集时间
		Date date = AUtils.getA20(rawBytes, pos);
		msg.setcTime(date);
		pos += 3;

		// F2:A14:d2-d7
		msg.setD2(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD3(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD4(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD5(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD6(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD7(AUtils.getA11(rawBytes, pos));
		pos += 4;
		
		// F3:A14:d8-d13
		msg.setD8(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD9(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD10(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD11(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD12(AUtils.getA14(rawBytes, pos));
		pos += 5;
		msg.setD13(AUtils.getA11(rawBytes, pos));
		pos += 4;	
		
		int year = msg.getcTime().getYear() + 1900; // 获取年
		/*
		 * 如果是1月1日上传的消息, 那么这个year还是当前的时间
		 * 	但是,本条消息中的时间应该是去年的了,所以,年份就要减去1
		 * 比如,2019年1月1日0:05分上传上来的消息,那么这个year就是2019,
		 * 	但是它包含的信息的年份应该还是2018年12月的,所以年份要减1
		 * 所以,只有在1月1日,才需要把年份减去1,下面的时间有月和日,只需要这个年
		 * 这里已经和樊工仔细论证过
		 */
		if(msg.getcTime().getMonth()==0 && msg.getcTime().getDate()==1){
			year--;
		}
		
		// F4:A23&A17:d14-d17
		msg.setD14(AUtils.getA23(rawBytes, pos));
		pos += 3;
		msg.setD15(AUtils.getA17(rawBytes, pos, year));
		pos += 4;
		msg.setD16(AUtils.getA23(rawBytes, pos));
		pos += 3;
		msg.setD17(AUtils.getA17(rawBytes, pos, year));
		pos += 4;
		
		// F5:A23&A17:d18-d21
		msg.setD18(AUtils.getA23(rawBytes, pos));
		pos += 3;
		msg.setD19(AUtils.getA17(rawBytes, pos, year));
		pos += 4;
		msg.setD20(AUtils.getA23(rawBytes, pos));
		pos += 3;
		msg.setD21(AUtils.getA17(rawBytes, pos, year));
		pos += 4;

		return msg;
	}

	// -------------------------------------------第3部分: 工具方法-----------------------------------------------------
	/**
	 * 获取报文中标注的长度(注意不是整个报文的长度,只是数据区的长度)
	 * 
	 * @param b1
	 *            整个报文的第2或4位
	 * @param b2
	 *            整个报文的第3或5位
	 */
	public static int getDeclaredLength(byte[] rawBytes) {
		byte b1 = rawBytes[1];
		byte b2 = rawBytes[2];
		int length = CodecUtils.byteToInt(b2) * 256 + CodecUtils.byteToInt(b1);
		return length >> 2;
	}

	/**
	 * 获得地址(数组形式)
	 * <p>
	 * 地址的格式是:A1+A2+采集点号,共5个字节
	 */
	public static byte[] getAddrBytes(byte[] rawBytes) {
		byte[] addr = new byte[5];
		// step1.获取行政区划码A1和终端地址A2, 共4个字节
		for (int i = 0; i <= 3; i++) {
			addr[i] = rawBytes[i + 7];
		}

		// step2.获取DA,也就是采集点号
		// (注意1,本项目中DA的第一个字节,变成十进制,只能是128,64,32,16,8,4,2,1这8种可能)
		// (注意2,总共可以有2040个采集点,但本系统中采集点最多带32个设备,所以,这里只用一个byte表示采集点号)
		// 参考376.1协议中4.3.4.4.2
		byte da1 = rawBytes[14]; // DA的两个字节
		byte da2 = rawBytes[15];

		// step3.处理DA1,得到采集点号
		byte collectPoint = 0; // 采集点号
		if (da1 == 128) { // DA1,决定了是哪一列
			collectPoint += 8;
		} else if (da1 == 64) {
			collectPoint += 7;
		} else if (da1 == 32) {
			collectPoint += 6;
		} else if (da1 == 16) {
			collectPoint += 5;
		} else if (da1 == 8) {
			collectPoint += 4;
		} else if (da1 == 4) {
			collectPoint += 3;
		} else if (da1 == 2) {
			collectPoint += 2;
		} else if (da1 == 1) {
			collectPoint += 1;
		}
		// step4.处理DA2
		collectPoint += (da2 - 1) * 8; // DA2决定了是哪一列

		// step5.返回结果
		addr[4] = collectPoint;
		return addr;
	}

	/**
	 * 获得地址(字符串形式)
	 */
	public static String getAddrString(byte[] rawBytes) {
		return CodecUtils.bytesToHexString(getAddrBytes(rawBytes));
	}

	/**
	 * 获得AFN应用层功能码
	 */
	public static byte getAFN(byte[] rawBytes) {
		return rawBytes[12];
	}

}
