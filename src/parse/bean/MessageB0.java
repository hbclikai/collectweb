package parse.bean;


/**
 * 实时上传的消息AFN=B0
 */
public class MessageB0 extends AbstractMessage {
	public static final int MESSAGE_LENGTH=74;	//B0消息中,每个小消息是74个字节
	
	//private Date r1;// 终端抄表时间[51 20 10 02 18] --> 18-02-10 20:51 (A15:年月日时分,没有秒)
	private double r2;// 当前总有功功率[00 00 00] --> 0 kW (A9:79.9999正负)
	private double r3;// 当前A相有功功率[00 00 00] --> 0 kW
	private double r4;// 当前B相有功功率[00 00 00] --> 0 kW
	private double r5;// 当前C相有功功率[00 00 00] --> 0 kW
	private double r6;// 当前总无功功率[00 00 00] --> 0 kvar
	private double r7;// 当前A相无功功率[00 00 00] --> 0 kvar
	private double r8;// 当前B相无功功率[00 00 00] --> 0 kvar
	private double r9;// 当前C相无功功率[00 00 00] --> 0 kvar
	private double r10;// 当前总功率因数[00 10] --> 100 % (A5:799.9正负)
	private double r11;// 当前A相功率因数[00 10] --> 100 %
	private double r12;// 当前B相功率因数[00 10] --> 100 %
	private double r13;// 当前C相功率因数[00 10] --> 100 %
	private double r14;// 当前A相电压[58 22] --> 225.8 V (A7: 999.9)
	private double r15;// 当前B相电压[00 00] --> 0 V
	private double r16;// 当前C相电压[00 00] --> 0 V
	private double r17;// 当前A相电流[00 00 00] --> 0 A (A25:799.999正负)
	private double r18;// 当前B相电流[00 00 00] --> 0 A
	private double r19;// 当前C相电流[00 00 00] --> 0 A
	private double r20;// A相电压相位角[00 00] (A5:799.9正负)
	private double r21;// B相电压相位角[00 00]
	private double r22;// C相电压相位角[00 00]
	private double r23;// A相电流相位角[00 00]
	private double r24;// B相电流相位角[00 00]
	private double r25;// C相电流相位角[00 00]
	private double r26;// 当前正向有功总电能示值[00 00 00 00 00] --> 0 kWh (A14:999999.9999)
	private double r27;// 当前反向有功总电能示值[00 00 00 00 00] --> 0 kWh
	
	
	public double getR2() {
		return r2;
	}
	public void setR2(double r2) {
		this.r2 = r2;
	}
	public double getR3() {
		return r3;
	}
	public void setR3(double r3) {
		this.r3 = r3;
	}
	public double getR4() {
		return r4;
	}
	public void setR4(double r4) {
		this.r4 = r4;
	}
	public double getR5() {
		return r5;
	}
	public void setR5(double r5) {
		this.r5 = r5;
	}
	public double getR6() {
		return r6;
	}
	public void setR6(double r6) {
		this.r6 = r6;
	}
	public double getR7() {
		return r7;
	}
	public void setR7(double r7) {
		this.r7 = r7;
	}
	public double getR8() {
		return r8;
	}
	public void setR8(double r8) {
		this.r8 = r8;
	}
	public double getR9() {
		return r9;
	}
	public void setR9(double r9) {
		this.r9 = r9;
	}
	public double getR10() {
		return r10;
	}
	public void setR10(double r10) {
		this.r10 = r10;
	}
	public double getR11() {
		return r11;
	}
	public void setR11(double r11) {
		this.r11 = r11;
	}
	public double getR12() {
		return r12;
	}
	public void setR12(double r12) {
		this.r12 = r12;
	}
	public double getR13() {
		return r13;
	}
	public void setR13(double r13) {
		this.r13 = r13;
	}
	public double getR14() {
		return r14;
	}
	public void setR14(double r14) {
		this.r14 = r14;
	}
	public double getR15() {
		return r15;
	}
	public void setR15(double r15) {
		this.r15 = r15;
	}
	public double getR16() {
		return r16;
	}
	public void setR16(double r16) {
		this.r16 = r16;
	}
	public double getR17() {
		return r17;
	}
	public void setR17(double r17) {
		this.r17 = r17;
	}
	public double getR18() {
		return r18;
	}
	public void setR18(double r18) {
		this.r18 = r18;
	}
	public double getR19() {
		return r19;
	}
	public void setR19(double r19) {
		this.r19 = r19;
	}
	public double getR20() {
		return r20;
	}
	public void setR20(double r20) {
		this.r20 = r20;
	}
	public double getR21() {
		return r21;
	}
	public void setR21(double r21) {
		this.r21 = r21;
	}
	public double getR22() {
		return r22;
	}
	public void setR22(double r22) {
		this.r22 = r22;
	}
	public double getR23() {
		return r23;
	}
	public void setR23(double r23) {
		this.r23 = r23;
	}
	public double getR24() {
		return r24;
	}
	public void setR24(double r24) {
		this.r24 = r24;
	}
	public double getR25() {
		return r25;
	}
	public void setR25(double r25) {
		this.r25 = r25;
	}
	public double getR26() {
		return r26;
	}
	public void setR26(double r26) {
		this.r26 = r26;
	}
	public double getR27() {
		return r27;
	}
	public void setR27(double r27) {
		this.r27 = r27;
	}
	@Override
	public String toString() {
		return "MessageB0 [r2=" + r2 + ", r3=" + r3 + ", r4=" + r4 + ", r5="
				+ r5 + ", r6=" + r6 + ", r7=" + r7 + ", r8=" + r8 + ", r9="
				+ r9 + ", r10=" + r10 + ", r11=" + r11 + ", r12=" + r12
				+ ", r13=" + r13 + ", r14=" + r14 + ", r15=" + r15 + ", r16="
				+ r16 + ", r17=" + r17 + ", r18=" + r18 + ", r19=" + r19
				+ ", r20=" + r20 + ", r21=" + r21 + ", r22=" + r22 + ", r23="
				+ r23 + ", r24=" + r24 + ", r25=" + r25 + ", r26=" + r26
				+ ", r27=" + r27 + ", getNotifyType()=" + getNotifyType()
				+ ", getDeviceId()=" + getDeviceId() + ", getGatewayId()="
				+ getGatewayId() + ", getServiceId()=" + getServiceId()
				+ ", getServiceType()=" + getServiceType()
				+ ", getEventTime()=" + getEventTime() + ", getcTime()="
				+ getcTime() + ", getId()=" + getId() + "]";
	}

	
}
