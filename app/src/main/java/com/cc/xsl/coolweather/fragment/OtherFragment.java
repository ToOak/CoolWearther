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

public class OtherFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d("OtherFragment onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.d("OtherFragment used onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("OtherFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other,container,false);
        LogUtil.d("OtherFragment onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d("OtherFragment onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("OtherFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("OtherFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("OtherFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("OtherFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("OtherFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("OtherFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d("OtherFragment onDetach");
    }
}
