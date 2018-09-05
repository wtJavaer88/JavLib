package com.wnc.javlib.jpa.entity;

import com.wnc.javlib.jpa.BaseEntity;
import org.apache.commons.lang.StringUtils;

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
public class JStar extends BaseEntity {
    @Id
    @Column(length = 95)
    private String starCode;
    private char head;
    private String name;
    @Transient
    private List<String> aliasName;
    private int aliasCount;
    private String aliasNameStr;

    @Transient
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "JMovieStarRls", joinColumns = {
            @JoinColumn(name = "star_code")}, inverseJoinColumns = {
            @JoinColumn(name = "movie_code")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"movie_code", "star_code"})})
    private List<JMovie> movies;

    public JStar() {
    }

    public JStar(char head, String star_code, String name) {
        super();
        this.head = head;
        this.starCode = star_code;
        this.name = name;
    }

    public List<JMovie> getMovies() {
        return movies;
    }

    public void setMovies(List<JMovie> movies) {
        this.movies = movies;
    }

    public JStar cvtAlias() {
        if (this.aliasName != null) {
            this.aliasCount = aliasName.size();
            this.aliasNameStr = StringUtils.join(aliasName, ",");
        }
        return this;
    }

    public char getHead() {
        return head;
    }

    public void setHead(char head) {
        this.head = head;
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

    public int getAliasCount() {
        return aliasCount;
    }

    public void setAliasCount(int aliasCount) {
        this.aliasCount = aliasCount;
    }

    public String getAliasNameStr() {
        return aliasNameStr;
    }

    public void setAliasNameStr(String aliasNameStr) {
        this.aliasNameStr = aliasNameStr;
    }
}
