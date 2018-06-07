package com.cc.xsl.coolweather.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cc.xsl.coolweather.BaseApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.activity.HomeDownActivity;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.util.LogUtil;

/**
 * Created by xushuailong on 2016/10/11.
 */
public class HomeFragement extends Fragment implements View.OnClickListener {

    Button homeDownBtn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.e("HomeFragment onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.e("HomeFragment used onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("HomeFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LogUtil.e("HomeFragment onCreateView");
        initview(view);
        viewEvents();
        return view;
    }

    private void viewEvents() {
        homeDownBtn.setOnClickListener(this);
    }

    private void initview(View view) {
        homeDownBtn = (Button) view.findViewById(R.id.home_down_btn);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.e("HomeFragment onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e("HomeFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("HomeFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e("HomeFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e("HomeFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e("HomeFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("HomeFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e("HomeFragment onDetach");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_down_btn: {
                startActivity(HomeDownActivity.getAction(BaseApplication.getApp()));
            }
        }
    }
}
