package parse.parser;

import java.util.List;

import parse.bean.AbstractMessage;
import parse.utils.CodecUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * parse包写了这么多类,但对外只提供本类的parse()方法就能解析了
 * <p>
 * 因为解析出的消息种类很多,所以返回值是List<AbstractMessage>类型
 */
public class NBPlatformMessagePaser {
	/**
	 * 接收一个JSONObject,返回List<AbstractMessage>
	 * <p>
	 * 这个方法其实就是调用了RawBytesParser类的readMessageFromBase64Str()方法,把电信平台的各个信息给加上
	 * <p>
	 * 本方法已经捕获了所有的异常,也就是说,如果解析出任何错误,会返回一个null,不会抛异常了
	 */
	public static List<AbstractMessage> parse(JSONObject jsonObj) {
		try {
			// ------------------------step1.解开json,得到NB给的各个属性--------------------------------
			JSONObject object1 = jsonObj.getJSONObject("service");
			String notifyType = jsonObj.getString("notifyType");
			String deviceId = jsonObj.getString("deviceId");
			String gatewayId = jsonObj.getString("gatewayId");

			JSONObject object2 = object1.getJSONObject("data");
			String serviceId = object1.getString("serviceId");
			String serviceType = object1.getString("serviceType");
			String eventTime = object1.getString("eventTime");
			String field1 = object2.getString("field1"); // 用base64编码的原始信息

			// ------------------------step2.调用RawBytesParser类来解析二进制内容------------------------
			List<AbstractMessage> msgs = RawBytesParser.readMessagesFromBase64Str(field1);

			// ------------------------step3.把电信NB平台的各个信息,也赋值到msg上去------------------------
			for (AbstractMessage msg : msgs) {
				msg.setNotifyType(notifyType);
				msg.setDeviceId(deviceId);
				msg.setGatewayId(gatewayId);
				msg.setServiceId(serviceId);
				msg.setServiceType(serviceType);
				msg.setEventTime(CodecUtils.getNBPlatformTime(eventTime));
			}

			// ------------------------step4.返回结果---------------------------------------------------
			return msgs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
