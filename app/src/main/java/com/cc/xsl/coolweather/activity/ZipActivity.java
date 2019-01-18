package com.cc.xsl.coolweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;
import com.cc.xsl.coolweather.util.LogUtil;
import com.cc.xsl.coolweather.util.ZipUtil;

import java.io.File;

public class ZipActivity extends BaseActivity {
    public static Intent getAction(Context context) {
        return new Intent(context, ZipActivity.class);
    }

    private Button compressBtn, uncompressBtn;

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        compressBtn = findViewById(R.id.compress_btn);
        uncompressBtn = findViewById(R.id.uncompress_btn);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.compress_btn: {
                String sourceFilePath = getExternalFilesDir("uncompress").getAbsolutePath();
                String zipFilePath = getExternalFilesDir("compress").getAbsolutePath() + File.separator + "abc.zip";
                ZipUtil.getInstance().compressZip4j(sourceFilePath, zipFilePath, "123abc");
                break;
            }
            case R.id.uncompress_btn: {
//                String zipFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "yasuobao.zip";
                String zipFilePath = getExternalFilesDir("compress").getAbsolutePath() + File.separator + "abc.zip";
                String sourceFilePath = getExternalFilesDir("uncompress").getAbsolutePath();
                LogUtil.e("uncompress: " + zipFilePath + "\t" + sourceFilePath);
                ZipUtil.getInstance().uncompressZip4j(
                        zipFilePath,
                        sourceFilePath,
                        "123abc"
                );
                break;
            }
        }
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zip;
    }
}
