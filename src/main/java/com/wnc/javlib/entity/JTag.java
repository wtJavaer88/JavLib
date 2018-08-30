package com.wnc.javlib.entity;

public class JTag {
	private int id;
	private String tagCode;
	private String tagName;

	public JTag() {

	}

	public JTag(String tagCode, String tagName) {
		super();
		this.tagCode = tagCode;
		this.tagName = tagName;
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
