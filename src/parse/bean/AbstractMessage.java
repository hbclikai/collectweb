package parse.bean;

import java.util.Date;

/**
 * Message的父类
 */
public abstract class AbstractMessage {
	// 这几个是电信平台post上来时除了原始内容又附带的信息,不在设备的byte[]中
	private String notifyType;
	private String deviceId;
	private String gatewayId;
	private String serviceId;
	private String serviceType;
	private Date eventTime;

	// 通用部分
	private Date cTime; // 采集时间,这个是设备给的时间,不是电信平台的时间
	private String id; // 这个是从byte[]中解析出的id

	// getter& setter
	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
