package com.cc.xsl.coolweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cc.xsl.coolweather.MyApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.activity.CaptureActivity;
import com.cc.xsl.coolweather.activity.HomeDownActivity;
import com.cc.xsl.coolweather.activity.LayoutActivity;
import com.cc.xsl.coolweather.activity.TextFoldActivity;
import com.cc.xsl.coolweather.activity.WidgetActivity;
import com.cc.xsl.coolweather.base.BaseFragment;
import com.cc.xsl.coolweather.util.LogUtil;

/**
 * Created by xushuailong on 2016/10/11.
 */
public class HomeFragement extends BaseFragment implements View.OnClickListener {

    Button homeDownBtn, textFoldBtn, layoutBtn, widgetBtn, captureBtn;

    @Override
    protected void initViews(View view) {
        homeDownBtn = view.findViewById(R.id.home_down_btn);
        homeDownBtn.setOnClickListener(this);
        textFoldBtn = view.findViewById(R.id.text_fold_btn);
        textFoldBtn.setOnClickListener(this);
        layoutBtn = view.findViewById(R.id.layout_btn);
        layoutBtn.setOnClickListener(this);
        widgetBtn = view.findViewById(R.id.widget_btn);
        widgetBtn.setOnClickListener(this);
        captureBtn = view.findViewById(R.id.capture_btn);
        captureBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_down_btn: {
                startActivity(HomeDownActivity.getAction(MyApplication.getApp()));
                break;
            }
            case R.id.text_fold_btn: {
                startActivity(TextFoldActivity.getIntent(MyApplication.getApp(), "新浪科技讯 北京时间7月25日凌晨消息，在今天举行的新产品发布会上，谷歌发布Android 4.3版本，代号仍为\"果冻豆(Jelly Bean)\"。今天发布的新一代Nexus 7将搭载该操作系统，Nexus系列设备今日可收到OTA推送更新。\n" +
                        "Android 4.3操作系统新增一系列功能。首先是多用户设置功能，包括针对保护儿童的“受限文件(restricted profiles)”特性。用户可以对应用内容进行限制，防止儿童在使用应用时看到不适宜内容，或接触不合适的应用内购买广告。这项功能与微软Windows Phone的\"儿童乐园(Microsoft's Kid's Corner)\"功能类似。\n" +
                        "第二项升级是智能蓝牙(Bluetooth Smart)功能，即\"低功耗蓝牙(Bluetooth Low Energy)\"。"));
                break;
            }
            case R.id.layout_btn: {
                startActivity(LayoutActivity.getAction(MyApplication.getApp()));
                break;
            }
            case R.id.widget_btn: {
                startActivity(WidgetActivity.getAction(MyApplication.getApp()));
                break;
            }
            case R.id.capture_btn: {
                startActivity(CaptureActivity.getAction(MyApplication.getApp()));
                break;
            }
        }
    }
}
