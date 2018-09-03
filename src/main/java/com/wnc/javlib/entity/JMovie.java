package com.wnc.javlib.entity;

import spiderqueue.core.DbEntity;

import java.util.List;

public class JMovie{
	private int id;
	private String movieCode;
	private String title;
	private String url;// 相对地址

	private String publishDate;
	private double score;
	private int length;// 影片长度
	private String monoImg;
	private String img;

	private List<JStar> stars;
	private List<JMakeDesc> makeDescs;// 制作信息, 包含导演,制片商,发行商
	private List<JTag> tags;// 标签

	private List<JavComment> comments;

	private int ownCount;// 拥有人数
	private int wantCount;// 想要人数
	private int viewedCount;// 看过人数

	private String director;
	private String maker;
	private String label;

	private int cnComments;
	private int cnReviews;

	private char singleStar;// 是否单体作品, Y或N, 或null(演员列表为空)


	public String getMonoImg() {
		return monoImg;
	}

	public void setMonoImg(String monoImg) {
		this.monoImg = monoImg;
	}

	public char getSingleStar() {
		return singleStar;
	}

	public void setSingleStar(char singleStar) {
		this.singleStar = singleStar;
	}

	public JMovie(String movie_code, String title, String url) {
		super();
		this.movieCode = movie_code;
		this.title = title;
		this.url = url;
	}

	public JMovie() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMovieCode() {
		return movieCode;
	}

	public void setMovieCode(String movie_code) {
		this.movieCode = movie_code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publish_date) {
		this.publishDate = publish_date;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<JStar> getStars() {
		return stars;
	}

	public void setStars(List<JStar> stars) {
		this.stars = stars;
	}

	public List<JMakeDesc> getMakeDescs() {
		return makeDescs;
	}

	public void setMakeDescs(List<JMakeDesc> makeDescs) {
		this.makeDescs = makeDescs;
	}

	public List<JTag> getTags() {
		return tags;
	}

	public void setTags(List<JTag> tags) {
		this.tags = tags;
	}

	public int getOwnCount() {
		return ownCount;
	}

	public void setOwnCount(int ownCount) {
		this.ownCount = ownCount;
	}

	public int getWantCount() {
		return wantCount;
	}

	public void setWantCount(int wantCount) {
		this.wantCount = wantCount;
	}

	public int getViewedCount() {
		return viewedCount;
	}

	public void setViewedCount(int viewedCount) {
		this.viewedCount = viewedCount;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCnComments() {
		return cnComments;
	}

	public void setCnComments(int cnComments) {
		this.cnComments = cnComments;
	}

	public int getCnReviews() {
		return cnReviews;
	}

	public void setCnReviews(int cnReviews) {
		this.cnReviews = cnReviews;
	}

	public List<JavComment> getComments() {
		return comments;
	}

	public void setComments(List<JavComment> comments) {
		this.comments = comments;
	}
}
