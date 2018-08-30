package com.wnc.javlib.entity;

public class JavComment {
    private String commentId;
    // 4000長度
    private String content;
    private JavUser user;
    private String date;

    private Integer type; // 0:review, 1:comment

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JavUser getUser() {
        return user;
    }

    public void setUser(JavUser user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
