package com.cc.xsl.coolweather.utils;

/**
 * Created by xushuailong on 2016/5/23.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
