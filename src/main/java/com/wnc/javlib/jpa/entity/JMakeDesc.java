package com.wnc.javlib.jpa.entity;

import com.wnc.javlib.enums.MakeDescEnum;
import com.wnc.javlib.jpa.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class JMakeDesc extends BaseEntity {
    @Id
    @Column(length = 95)
    private String mdCode;
    private String mdName;

    // 影片制作类型, 导演,制作商,发行商
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MakeDescEnum mdEnum;

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
