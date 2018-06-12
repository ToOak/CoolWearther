package com.cc.xsl.coolweather.model;

import java.io.Serializable;

/**
 * Created by xushuailong on 2016/10/11.
 */

public class Msg implements Serializable {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;
    private static final long serialVersionUID = 2375707796284689367L;
    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
