package com.wnc.javlib.entity;
//http://v22q.com/cn/user.php?u=qiqutt
public class JavUser {
    private String uid;
    private String nick;
    private String country;
    // 发表文章数
    private Integer userposts;

    private String desc;
    private String province;
    private String city;
    //想要影片
    private Integer want;
    //看过
    private Integer watched;

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getUserposts() {
        return userposts;
    }

    public void setUserposts(Integer userposts) {
        this.userposts = userposts;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getWant() {
        return want;
    }

    public void setWant(Integer want) {
        this.want = want;
    }
}
