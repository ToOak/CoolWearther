package com.cc.xsl.coolweather.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.cc.xsl.coolweather.MyApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.activity.AudioActivity;
import com.cc.xsl.coolweather.activity.BlueToothActivity;
import com.cc.xsl.coolweather.activity.CarrouselActivity;
import com.cc.xsl.coolweather.activity.HomeDownActivity;
import com.cc.xsl.coolweather.activity.LayoutActivity;
import com.cc.xsl.coolweather.activity.TextFoldActivity;
import com.cc.xsl.coolweather.activity.View2BitmapActivity;
import com.cc.xsl.coolweather.activity.WidgetActivity;
import com.cc.xsl.coolweather.activity.ZipActivity;
import com.cc.xsl.coolweather.base.BaseFragment;

/**
 * Created by xushuailong on 2016/10/11.
 */
public class HomeFragement extends BaseFragment implements View.OnClickListener {

    Button homeDownBtn, textFoldBtn, layoutBtn, widgetBtn, carrouselBtn,
            audioBtn, zipBtn, viewToBitmapBtn, blueToothBtn;
    ScrollView sc;
    ImageView toTopIv, toBottomIv;

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
        carrouselBtn = view.findViewById(R.id.carrousel_btn);
        carrouselBtn.setOnClickListener(this);
        audioBtn = view.findViewById(R.id.audio_btn);
        audioBtn.setOnClickListener(this);
        zipBtn = view.findViewById(R.id.zip_btn);
        zipBtn.setOnClickListener(this);
        sc = view.findViewById(R.id.sc);
        toTopIv = view.findViewById(R.id.to_top_iv);
        blueToothBtn = view.findViewById(R.id.blue_tooth_btn);
        blueToothBtn.setOnClickListener(this);
        toBottomIv = view.findViewById(R.id.to_bottom_iv);
        viewToBitmapBtn = view.findViewById(R.id.view_to_bitmap_btn);
        viewToBitmapBtn.setOnClickListener(this);
        toBottomIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc.post(new Runnable() {
                    @Override
                    public void run() {
                        sc.post(new Runnable() {
                            @Override
                            public void run() {
                                // 滚动到底部
                                sc.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                });
            }
        });
        toTopIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sc.post(new Runnable() {
                    @Override
                    public void run() {
                        sc.post(new Runnable() {
                            @Override
                            public void run() {
                                // 滚动至顶部
                                sc.fullScroll(ScrollView.FOCUS_UP);
                            }
                        });
                    }
                });
            }
        });
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
            case R.id.carrousel_btn: {
                startActivity(CarrouselActivity.getAction(MyApplication.getApp()));
                break;
            }
            case R.id.audio_btn: {
                startActivity(AudioActivity.getAction(MyApplication.getApp()));
                break;
            }
            case R.id.zip_btn: {
                startActivity(ZipActivity.getAction(MyApplication.getApp()));
                break;
            }
            case R.id.view_to_bitmap_btn: {
                startActivity(View2BitmapActivity.getAction(MyApplication.getApp()));
                break;
            }
            case R.id.blue_tooth_btn: {
                startActivity(BlueToothActivity.getAction(MyApplication.getApp()));
                break;
            }
        }
    }
}
