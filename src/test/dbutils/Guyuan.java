package test.dbutils;

import java.util.Date;

public class Guyuan {
	private int id;
	private String name;
	private String zhiwei;
	private int lingdao;
	private Date ruzhishijian;
	private double gongzi;
	private double ticheng;
	private int bumenid;
	public Guyuan() {
		
	}
	public Guyuan(int id, String name, String zhiwei, int lingdao, Date ruzhishijian, double gongzi, double ticheng,
			int bumenid) {
		super();
		this.id = id;
		this.name = name;
		this.zhiwei = zhiwei;
		this.lingdao = lingdao;
		this.ruzhishijian = ruzhishijian;
		this.gongzi = gongzi;
		this.ticheng = ticheng;
		this.bumenid = bumenid;
	}
	
	@Override
	public String toString() {
		return "Guyuan [id=" + id + ", name=" + name + ", zhiwei=" + zhiwei + ", lingdao=" + lingdao + ", ruzhishijian="
				+ ruzhishijian + ", gongzi=" + gongzi + ", ticheng=" + ticheng + ", bumenid=" + bumenid + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZhiwei() {
		return zhiwei;
	}
	public void setZhiwei(String zhiwei) {
		this.zhiwei = zhiwei;
	}
	public int getLingdao() {
		return lingdao;
	}
	public void setLingdao(int lingdao) {
		this.lingdao = lingdao;
	}
	public Date getRuzhishijian() {
		return ruzhishijian;
	}
	public void setRuzhishijian(Date ruzhishijian) {
		this.ruzhishijian = ruzhishijian;
	}
	public double getGongzi() {
		return gongzi;
	}
	public void setGongzi(double gongzi) {
		this.gongzi = gongzi;
	}
	public double getTicheng() {
		return ticheng;
	}
	public void setTicheng(double ticheng) {
		this.ticheng = ticheng;
	}
	public int getBumenid() {
		return bumenid;
	}
	public void setBumenid(int bumenid) {
		this.bumenid = bumenid;
	}
	
}
