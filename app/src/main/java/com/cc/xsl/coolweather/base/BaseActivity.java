package com.cc.xsl.coolweather.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.xsl.coolweather.HeaderStyle;
import com.cc.xsl.coolweather.MyApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.util.LogUtil;
import com.cc.xsl.coolweather.util.ResUtil;


/**
 * Created by xushuailong on 2016/10/10.
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends Activity {
    protected Context context;
    protected LayoutInflater mInflater;
    protected TextView backTv, rightTv, titleTv;
    protected ImageView backIv, rightIv;
    protected View headerView, backView, rightView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.e("this is " + getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyApplication.getApp().addActivity(this);

        context = MyApplication.getContext();
        mInflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_base_layout);
        initviews();
        initLayout();
        initHearder();

    }

    private void initviews() {
        backIv = (ImageView) findViewById(R.id.back_iv);
        backTv = (TextView) findViewById(R.id.back_tv);
        rightIv = (ImageView) findViewById(R.id.right_iv);
        rightTv = (TextView) findViewById(R.id.right_tv);
        headerView = findViewById(R.id.comment_header_view);
        titleTv = (TextView) findViewById(R.id.title_tv);
        backView = findViewById(R.id.back_view);
        rightView = findViewById(R.id.right_view);

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightBtnClick();
            }
        });
    }

    protected void onRightBtnClick() {

    }


    private void initHearder() {
        if (showCommentHeader()) {
            titleTv.setText(getUiTitle());
            headerView.setVisibility(View.VISIBLE);
            if (getRightBtnRes() != -1) {
                rightView.setVisibility(View.VISIBLE);
                String sourceType = getResources().getResourceTypeName(getRightBtnRes());
                if (sourceType.contains("drawable")) {
                    rightIv.setVisibility(View.VISIBLE);
                    rightIv.setBackgroundResource(getRightBtnRes());
                    rightTv.setVisibility(View.GONE);
                } else if (sourceType.contains("string")) {
                    rightIv.setVisibility(View.GONE);
                    rightTv.setVisibility(View.VISIBLE);
                    rightTv.setText(getRightBtnRes());
                }
            } else {
                rightView.setVisibility(View.GONE);
            }
            switch (getHeaderStyle()) {
                case WHITE:
                    backIv.setBackgroundResource(R.drawable.bg_back_red_btn);
                    headerView.setBackgroundColor(ResUtil.getColor(R.color.white));
                    rightTv.setTextColor(ResUtil.getColor(R.color.header_red_bg));
                    backTv.setTextColor(ResUtil.getColor(R.color.header_red_bg));
                    titleTv.setTextColor(ResUtil.getColor(R.color.header_red_bg));
                    break;
                case RED:
                    headerView.setBackgroundColor(ResUtil.getColor(R.color.header_red_bg));
                    rightTv.setTextColor(ResUtil.getColor(R.color.white));
                    backTv.setTextColor(ResUtil.getColor(R.color.white));
                    backIv.setBackgroundResource(R.drawable.bg_back_white_btn);
                    titleTv.setTextColor(ResUtil.getColor(R.color.white));
                    break;
            }
        } else {
            headerView.setVisibility(View.GONE);
        }
    }

    /**
     * @return
     */
    protected HeaderStyle getHeaderStyle() {
        return HeaderStyle.RED;
    }

    /**
     * 设置右边的图片资源或者文案资源
     *
     * @return 图片或文案资源
     */
    protected int getRightBtnRes() {
        return -1;
    }

    /**
     * title
     *
     * @return
     */
    protected String getUiTitle() {
        return this.getClass().getSimpleName();
    }

    /**
     * 是否显示公共title
     *
     * @return
     */
    protected boolean showCommentHeader() {
        return true;
    }

    private void initLayout() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.content_vs);
        viewStub.setLayoutResource(getLayoutId());
        viewStub.inflate();
    }

    /**
     * activity 公共布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApp().removeActivity(this);
    }

    /**
     * 退出方法
     */
    public void hideApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }





}
