package com.cc.xsl.coolweather.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.util.LogUtil;

/**
 * TODO 没有用support-v4下的fragment
 */
public abstract class BaseFragment extends android.app.Fragment {
    private String tag = this.getClass().getSimpleName();
    private ViewStub contentVs;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.e(tag + " onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.e(tag + " used onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("HomeFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_layout, container, false);
        LogUtil.e(tag + " onCreateView");
        contentVs = (ViewStub) view.findViewById(R.id.content_vs);
        initLayout(view);
        initViews(view);
        return view;
    }

    /**
     * fragment 布局初始化
     *
     * @param view
     */
    protected abstract void initViews(View view);

    private void initLayout(View view) {
        contentVs.setLayoutResource(getLayoutId());
        contentVs.inflate();
    }

    /**
     * fragment 的layout布局
     *
     * @return
     */
    protected abstract int getLayoutId();


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.e(tag + " onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e(tag + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(tag + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(tag + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(tag + " onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e(tag + " onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(tag + " onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(tag + " onDetach");
    }
}
