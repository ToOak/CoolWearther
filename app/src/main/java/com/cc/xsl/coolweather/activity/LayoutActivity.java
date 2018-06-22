package com.cc.xsl.coolweather.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.activity.layout.AbsoluteActivity;
import com.cc.xsl.coolweather.activity.layout.FrameActivity;
import com.cc.xsl.coolweather.activity.layout.GridActivity;
import com.cc.xsl.coolweather.activity.layout.LinearActivity;
import com.cc.xsl.coolweather.activity.layout.RelativeActivity;
import com.cc.xsl.coolweather.activity.layout.TableActivity;
import com.cc.xsl.coolweather.base.BaseActivity;

public class LayoutActivity extends BaseActivity {
    public static Intent getAction(Context context) {
        Intent intent = new Intent(context, LayoutActivity.class);
        return intent;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_layout;
    }

    /*
     * 1.作用域必须是public 2.返回类型必须是void 3.参数必须是View对象
     */
    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLinearLayout: {
                Intent intent = new Intent(this, LinearActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonRelativeLayout: {
                Intent intent = new Intent(this, RelativeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonFrameLayout: {
                Intent intent = new Intent(this, FrameActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonAbsoluteLayout: {
                Intent intent = new Intent(this, AbsoluteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonTableLayout: {
                Intent intent = new Intent(this, TableActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.buttonGridLayout: {
                Intent intent = new Intent(this, GridActivity.class);
                startActivity(intent);
                break;
            }

            default:
                break;
        }
    }
}
