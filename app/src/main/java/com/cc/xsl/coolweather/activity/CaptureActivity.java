package com.cc.xsl.coolweather.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;

public class CaptureActivity extends BaseActivity implements View.OnClickListener {

    private TextView backBtn;

    public static Intent getAction(Context context) {
        Intent intent = new Intent(context, CaptureActivity.class);
        return intent;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(this);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_capture;
    }

    @Override
    protected boolean showCommentHeader() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn: {
                this.finish();
                break;
            }
        }
    }
}
