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
public class JTag extends BaseEntity {
    @Id
    @Column(length = 95)
    private String tagCode;
    private String tagName;

    @Transient
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "JMovieTagRls", joinColumns = {
            @JoinColumn(name = "tag_code")}, inverseJoinColumns = {
            @JoinColumn(name = "movie_code")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"movie_code", "tag_code"})})
    private List<JMovie> movies;

    public JTag() {

    }

    public JTag(String tagCode, String tagName) {
        super();
        this.tagCode = tagCode;
        this.tagName = tagName;
    }

    public List<JMovie> getMovies() {
        return movies;
    }

    public void setMovies(List<JMovie> movies) {
        this.movies = movies;
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

}
