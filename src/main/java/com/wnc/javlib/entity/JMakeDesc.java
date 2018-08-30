package com.wnc.javlib.entity;

public class JMakeDesc {
	// 影片制作类型, 导演,制作商,发行商
	private MakeDescEnum mdEnum;
	private String mdCode;
	private String mdName;

	public JMakeDesc() {

	}

	public JMakeDesc(MakeDescEnum mdEnum, String mdCode, String mdName) {
		super();
		this.mdEnum = mdEnum;
		this.mdCode = mdCode;
		this.mdName = mdName;
	}

	public JMakeDesc(MakeDescEnum mdEnum, String mdCode) {
		super();
		this.mdEnum = mdEnum;
		this.mdCode = mdCode;
	}

	public MakeDescEnum getMdEnum() {
		return mdEnum;
	}

	public void setMdEnum(MakeDescEnum mdEnum) {
		this.mdEnum = mdEnum;
	}

	public String getMdCode() {
		return mdCode;
	}

	public void setMdCode(String mdCode) {
		this.mdCode = mdCode;
	}

	public String getMdName() {
		return mdName;
	}

	public void setMdName(String mdName) {
		this.mdName = mdName;
	}
}
