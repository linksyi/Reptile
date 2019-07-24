package com.reptile.demo.entity;

/**
 * @Author: Mr.ZuoY
 * @Description:
 * @Date: 2019/2/20 16:12
 */
public enum RecordRr {
    AT("@"),xing("*"),A("A");

    private final String value;

    RecordRr(String value)
    {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return this.value;
    }
}
