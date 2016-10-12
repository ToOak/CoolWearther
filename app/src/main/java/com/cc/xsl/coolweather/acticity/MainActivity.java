package com.cc.xsl.coolweather.acticity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.xsl.coolweather.BaseApplication;
import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.fragment.HomeFragement;
import com.cc.xsl.coolweather.fragment.OtherFragment;
import com.cc.xsl.coolweather.util.LogUtil;
import com.cc.xsl.coolweather.util.ResUtil;
import com.cc.xsl.coolweather.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xushuailong on 2016/10/10.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleBack, titleContent, titleEdit, homeBtn, otherBtn;
    private Fragment homeFragment, otherFragment;
    private FragmentManager fragmentManager;

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
        initData();
        initView();
        viewEvent();
    }

    /**
     * 处理页面之间的数据传递
     */
    private void initData() {
        Intent intent = getIntent();
        LogUtil.d(intent.getStringExtra("param1") + "\t" + intent.getStringExtra("param2"));
    }

    private void initView() {
        titleBack = (TextView) findViewById(R.id.title).findViewById(R.id.title_back);
        titleContent = (TextView) findViewById(R.id.title).findViewById(R.id.title_content);
        titleEdit = (TextView) findViewById(R.id.title).findViewById(R.id.title_edit);
        homeBtn = (TextView) findViewById(R.id.btn_home);
        otherBtn = (TextView) findViewById(R.id.btn_other);
//        showHomeFragment();
    }

    private void viewEvent() {
        titleContent.setText(ResUtil.getString(R.string.home));
        titleBack.setText(ResUtil.getString(R.string.exit));
//        titleEdit.setText(ResUtil.getString(R.string.talking));
        titleEdit.setVisibility(View.GONE);
        titleBack.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        otherBtn.setOnClickListener(this);
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
            case R.id.talking:{
                Intent intent = new Intent(this,TalkActivity.class);
                startActivity(intent);
                break;
            }
            default:{
                ToastUtil.showErrorMessage();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:{

                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setCancelable(true);
//                dialog.setTitle(ResUtil.getString(R.string.sure_exit));
                dialog.setMessage(R.string.sure_exit);
                dialog.setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showErrorMessage(null, ResUtil.getString(R.string.bye));
                        BaseApplication.getApp().finishAll();
                    }
                });
                dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
//                dialog.show();
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                break;
            }
            case R.id.btn_home:{
                showHomeFragment();
                break;
            }
            case R.id.btn_other:{
                showOtherFragment();
                break;
            }
        }
    }

    private void showHomeFragment() {
        if (fragmentManager == null){
            fragmentManager = getFragmentManager();
        }
        HomeFragement fragement = new HomeFragement();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout,fragement);
        transaction.commit();
        homeBtn.setEnabled(false);
        otherBtn.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LogUtil.d("onBackPressed");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.d("onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK){
            LogUtil.d("keyCode == KeyEvent.KEYCODE_BACK");
            hideApp();
            // return is "true", intercept
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showOtherFragment() {
        if (fragmentManager == null){
            fragmentManager = getFragmentManager();
        }
        OtherFragment fragment = new OtherFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout,fragment);
//        transaction.addToBackStack("other");
        transaction.commit();
        otherBtn.setEnabled(false);
        homeBtn.setEnabled(true);
    }
}
