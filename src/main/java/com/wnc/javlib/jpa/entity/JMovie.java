package com.wnc.javlib.jpa.entity;

import com.wnc.javlib.jpa.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Entity
public class JMovie extends BaseEntity {
    @Id
    @Column(length = 95)
    private String movieCode;
    @Column(length = 4000)
    private String title;
    private String url;// 相对地址

    private String publishDate;
    private double score;
    private int length;// 影片长度
    @Column(length = 4000)
    private String monoImg;
    @Column(length = 4000)
    private String img;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "JMovieStarRls", joinColumns = {
            @JoinColumn(name = "movie_code")}, inverseJoinColumns = {
            @JoinColumn(name = "star_code")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"movie_code", "star_code"})})
    private List<JStar> stars;

    @Transient
    private List<JMakeDesc> makeDescs;// 制作信息, 包含导演,制片商,发行商

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "JMovieTagRls", joinColumns = {
            @JoinColumn(name = "movie_code")}, inverseJoinColumns = {
            @JoinColumn(name = "tag_code")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"movie_code", "tag_code"})})
    private List<JTag> tags;// 标签

    @Transient
    private List<JavComment> comments;

    @Column(columnDefinition="tinyint default 0")
    private int hasTorrent = 0;
    private int cmtPics = 0;
    private int cmtCount = 0;

    private int ownCount;// 拥有人数
    private int wantCount;// 想要人数
    private int viewedCount;// 看过人数

    private String director;
    private String maker;
    private String label;

    private int cnComments;
    private int cnReviews;

    private char singleStar = '-';// 是否单体作品, Y或N, 或'-'(演员列表为空)

    public JMovie(String movieCode, String title, String url) {
        super();
        this.movieCode = movieCode;
        this.title = title;
        this.url = url;
    }

    public JMovie() {
    }

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

    public int getHasTorrent() {
        return hasTorrent;
    }

    public void setHasTorrent(int hasTorrent) {
        this.hasTorrent = hasTorrent;
    }

    public int getCmtPics() {
        return cmtPics;
    }

    public void setCmtPics(int cmtPics) {
        this.cmtPics = cmtPics;
    }

    public int getCmtCount() {
        return cmtCount;
    }

    public void setCmtCount(int cmtCount) {
        this.cmtCount = cmtCount;
    }
}
