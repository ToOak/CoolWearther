package com.cc.xsl.coolweather.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class View2BitmapActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_jietu_pic;

    public static Intent getAction(Context context) {
        return new Intent(context, View2BitmapActivity.class);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.bt_save).setOnClickListener(this);
        ll_jietu_pic = (LinearLayout) findViewById(R.id.ll_jietu_pic);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_to_bitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save: {
                //相关权限的申请 存储权限
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        Toast.makeText(this, "正在保存图片...", Toast.LENGTH_SHORT).show();
                        saveMyBitmap("AuthCode", createViewBitmap(ll_jietu_pic));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    //权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "正在保存图片...", Toast.LENGTH_SHORT).show();
                    try {
                        saveMyBitmap("AuthCode", createViewBitmap(ll_jietu_pic));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
//                    tvSave.setClickable(true);
                    Toast.makeText(this, "请先开启读写权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String picFile = (String) msg.obj;
            String[] split = picFile.split("/");
            String fileName = split[split.length - 1];
            try {
                MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), picFile, fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
// 最后通知图库更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
                    + picFile)));
            Toast.makeText(View2BitmapActivity.this, "图片保存图库成功", Toast.LENGTH_LONG).show();
        }
    };

    //使用IO流将bitmap对象存到本地指定文件夹
    public void saveMyBitmap(final String bitName, final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filePath = Environment.getExternalStorageDirectory().getPath();
                File file = new File(filePath + "/DCIM/Camera/" + bitName + ".png");
                try {
                    file.createNewFile();

                    FileOutputStream fOut = null;
                    fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);


                    Message msg = Message.obtain();
                    msg.obj = file.getPath();
                    handler.sendMessage(msg);
//Toast.makeText(PayCodeActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
