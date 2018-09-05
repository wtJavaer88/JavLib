package com.wnc.javlib.enums;

public enum MakeDescEnum {
    // 导演, 制作商, 发行商
    DIRECTOR(1), MAKER(2), LABEL(3);
    int type;

    MakeDescEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
