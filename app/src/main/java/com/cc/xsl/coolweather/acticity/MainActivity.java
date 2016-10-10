package com.cc.xsl.coolweather.acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cc.xsl.coolweather.BaseApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.util.LogUtil;
import com.cc.xsl.coolweather.util.ResUtil;
import com.cc.xsl.coolweather.util.ToastUtil;

/**
 * Created by xushuailong on 2016/10/10.
 */
public class MainActivity extends BaseActivity {

    public static void actionStart(Context context,String data1,String data2){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Intent intent = getIntent();
        LogUtil.d(intent.getStringExtra("param1") + "\t" + intent.getStringExtra("param2"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:{
                ToastUtil.showMessage(R.string.about);
                break;
            }
            case R.id.exit:{
                ToastUtil.showErrorMessage(null, ResUtil.getString(R.string.bye));
                BaseApplication.getApp().finishAll();
                break;
            }
            default:{
                ToastUtil.showErrorMessage();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
