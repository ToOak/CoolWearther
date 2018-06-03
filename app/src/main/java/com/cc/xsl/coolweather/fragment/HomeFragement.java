package com.cc.xsl.coolweather.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.util.LogUtil;

/**
 * Created by xushuailong on 2016/10/11.
 */
public class HomeFragement extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d("HomeFragment onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.d("HomeFragment used onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("HomeFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LogUtil.d("HomeFragment onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d("HomeFragment onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("HomeFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("HomeFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("HomeFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("HomeFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("HomeFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("HomeFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d("HomeFragment onDetach");
    }

}
