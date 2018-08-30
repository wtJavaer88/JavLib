package com.wnc.javlib.entity;

import spiderqueue.core.DbEntity;

import java.util.List;

public class JStar implements DbEntity {
	private int id;
	private char head;
	private String starCode;
	private String name;
	private List<String> aliasName;
	private Integer mvPages;// 出演影片页数
	private String createDate;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public JStar() {
		super();
	}

	public JStar(char head, String star_code, String name) {
		super();
		this.head = head;
		this.starCode = star_code;
		this.name = name;
	}

	public char getHead() {
		return head;
	}

	public void setHead(char head) {
		this.head = head;
	}

	public String getLogKey() {
		return "[" + starCode + "-" + name + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStarCode() {
		return starCode;
	}

	public void setStarCode(String star_code) {
		this.starCode = star_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAliasName() {
		return aliasName;
	}

	public void setAliasName(List<String> aliasName) {
		this.aliasName = aliasName;
	}

	public Integer getMvPages() {
		return mvPages;
	}

	public void setMvPages(Integer mvPages) {
		this.mvPages = mvPages;
	}
}
