package com.cc.xsl.coolweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cc.xsl.coolweather.MyApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.activity.HomeDownActivity;
import com.cc.xsl.coolweather.base.BaseFragment;
import com.cc.xsl.coolweather.util.LogUtil;

/**
 * Created by xushuailong on 2016/10/11.
 */
public class HomeFragement extends BaseFragment implements View.OnClickListener {

    Button homeDownBtn, textFoldBtn;

    @Override
    protected void initViews(View view) {
        homeDownBtn = (Button) view.findViewById(R.id.home_down_btn);
        homeDownBtn.setOnClickListener(this);
        textFoldBtn = (Button) view.findViewById(R.id.text_fold_btn);
        textFoldBtn.setOnClickListener(this);
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

                break;
            }
        }
    }
}
