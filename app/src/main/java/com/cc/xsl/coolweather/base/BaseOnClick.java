package com.cc.xsl.coolweather.base;

import android.view.View;

import com.cc.xsl.coolweather.util.ToastUtil;

/**
 * Created by xushuailong on 2016/10/17.
 */
public class BaseOnClick implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        ToastUtil.showMessage(this.getClass().getName());
    }
}
