package parse.bean;

import java.util.Date;

/**
 * 实时上传的消息AFN=B1
 */
public class MessageB1 extends AbstractMessage {
	public static final int MESSAGE_LENGTH = 89; // B1消息中,每个小消息是89个字节

	// private Date d1;// 本次冻结数据的日期 (A20: 只有年月日)
	private double d2;// 当前正向有功总电能示值[00 00 00 00 00] --------> 0 kWh
						// (A14:999999.9999)
	private double d3;// 当前费率1正向有功电能示值[00 00 00 00 00] --------> 0 kWh 尖
	private double d4;// 当前费率2正向有功电能示值[00 00 00 00 00] --------> 0 kWh 峰
	private double d5;// 当前费率3正向有功电能示值[00 00 00 00 00] --------> 0 kWh 谷
	private double d6;// 当前费率4正向有功电能示值[00 00 00 00 00] --------> 0 kWh 平
	private double d7;// 当前正向无功（组合无功1）总电能示值[00 00 00 00] --------> 0 kvarh
						// (A11:999999.99)
	private double d8;// 当前反向有功总电能示值[00 00 00 00 00] --------> 0 kWh (A14:
						// 999999.9999)
	private double d9;// 当前费率1反向有功电能示值[00 00 00 00 00] --------> 0 kWh 尖
	private double d10;// 当前费率2反向有功电能示值[00 00 00 00 00] --------> 0 kWh 峰
	private double d11;// 当前费率3反向有功电能示值[00 00 00 00 00] --------> 0 kWh 谷
	private double d12;// 当前费率4反向有功电能示值[00 00 00 00 00] --------> 0 kWh 平
	private double d13;// 当前反向无功（组合无功2）总电能示值[00 00 00 00] --------> 0 kvarh
						// (A11:999999.99)
	private double d14;// 当月正向有功总最大需量[00 00 00] --------> 0 kW (A23: 99.9999)
	private Date d15;// 当月正向有功总最大需量发生时间[00 00 00 00] --------> 00-00 00:00 (A17:
						// 月日时分)
	private double d16;// 当月正向无功总最大需量[00 00 00] --------> 0 kvar (A23: 99.9999)
	private Date d17;// 当月正向无功总最大需量发生时间[00 00 00 00] --------> 00-00 00:00(A17:
						// 月日时分)
	private double d18;// 当月反向有功总最大需量[00 00 00] --------> 0 kW (A23: 99.9999)
	private Date d19;// 当月反向有功总最大需量发生时间[00 00 00 00] --------> 00-00 00:00(A17:
						// 月日时分)
	private double d20;// 当月反向无功总最大需量[00 00 00] --------> 0 kvar (A23: 99.9999)
	private Date d21;// 当月反向无功总最大需量发生时间[00 00 00 00] --------> 00-00 00:00(A17:
						// 月日时分)

	public double getD2() {
		return d2;
	}

	public void setD2(double d2) {
		this.d2 = d2;
	}

	public double getD3() {
		return d3;
	}

	public void setD3(double d3) {
		this.d3 = d3;
	}

	public double getD4() {
		return d4;
	}

	public void setD4(double d4) {
		this.d4 = d4;
	}

	public double getD5() {
		return d5;
	}

	public void setD5(double d5) {
		this.d5 = d5;
	}

	public double getD6() {
		return d6;
	}

	public void setD6(double d6) {
		this.d6 = d6;
	}

	public double getD7() {
		return d7;
	}

	public void setD7(double d7) {
		this.d7 = d7;
	}

	public double getD8() {
		return d8;
	}

	public void setD8(double d8) {
		this.d8 = d8;
	}

	public double getD9() {
		return d9;
	}

	public void setD9(double d9) {
		this.d9 = d9;
	}

	public double getD10() {
		return d10;
	}

	public void setD10(double d10) {
		this.d10 = d10;
	}

	public double getD11() {
		return d11;
	}

	public void setD11(double d11) {
		this.d11 = d11;
	}

	public double getD12() {
		return d12;
	}

	public void setD12(double d12) {
		this.d12 = d12;
	}

	public double getD13() {
		return d13;
	}

	public void setD13(double d13) {
		this.d13 = d13;
	}

	public double getD14() {
		return d14;
	}

	public void setD14(double d14) {
		this.d14 = d14;
	}

	public Date getD15() {
		return d15;
	}

	public void setD15(Date d15) {
		this.d15 = d15;
	}

	public double getD16() {
		return d16;
	}

	public void setD16(double d16) {
		this.d16 = d16;
	}

	public Date getD17() {
		return d17;
	}

	public void setD17(Date d17) {
		this.d17 = d17;
	}

	public double getD18() {
		return d18;
	}

	public void setD18(double d18) {
		this.d18 = d18;
	}

	public Date getD19() {
		return d19;
	}

	public void setD19(Date d19) {
		this.d19 = d19;
	}

	public double getD20() {
		return d20;
	}

	public void setD20(double d20) {
		this.d20 = d20;
	}

	public Date getD21() {
		return d21;
	}

	public void setD21(Date d21) {
		this.d21 = d21;
	}

	@Override
	public String toString() {
		return "MessageB1 [d2=" + d2 + ", d3=" + d3 + ", d4=" + d4 + ", d5="
				+ d5 + ", d6=" + d6 + ", d7=" + d7 + ", d8=" + d8 + ", d9="
				+ d9 + ", d10=" + d10 + ", d11=" + d11 + ", d12=" + d12
				+ ", d13=" + d13 + ", d14=" + d14 + ", d15=" + d15 + ", d16="
				+ d16 + ", d17=" + d17 + ", d18=" + d18 + ", d19=" + d19
				+ ", d20=" + d20 + ", d21=" + d21 + ", getNotifyType()="
				+ getNotifyType() + ", getDeviceId()=" + getDeviceId()
				+ ", getGatewayId()=" + getGatewayId() + ", getServiceId()="
				+ getServiceId() + ", getServiceType()=" + getServiceType()
				+ ", getEventTime()=" + getEventTime() + ", getcTime()="
				+ getcTime() + ", getId()=" + getId() + "]";
	}

}
