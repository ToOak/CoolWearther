package com.cc.xsl.coolweather.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cc.xsl.coolweather.R;
import com.cc.xsl.coolweather.base.BaseFragment;
import com.cc.xsl.coolweather.util.LogUtil;
import com.cc.xsl.coolweather.util.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by xushuailong on 2016/10/11.
 */

public class OtherFragment extends BaseFragment implements View.OnClickListener {

    private Button saveFilesBtn, saveCachesBtn, getFilesBtn, getCachesBtn, samePathBtn;

    @Override
    protected void initViews(View view) {
        saveCachesBtn = (Button) view.findViewById(R.id.save_caches_btn);
        saveFilesBtn = (Button) view.findViewById(R.id.save_files_btn);
        getCachesBtn = (Button) view.findViewById(R.id.get_caches_btn);
        getFilesBtn = (Button) view.findViewById(R.id.get_files_btn);
        samePathBtn = (Button) view.findViewById(R.id.same_path_btn);

        saveCachesBtn.setOnClickListener(this);
        saveFilesBtn.setOnClickListener(this);
        getFilesBtn.setOnClickListener(this);
        getCachesBtn.setOnClickListener(this);
        samePathBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other;
    }

    /**
     * Environment.getDataDirectory() = /data
     * Environment.getDownloadCacheDirectory() = /cache
     * Environment.getExternalStorageDirectory() = /mnt/sdcard
     * Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
     * Environment.getRootDirectory() = /system
     * getPackageCodePath() = /data/app/com.my.app-1.apk
     * getPackageResourcePath() = /data/app/com.my.app-1.apk
     * getCacheDir() = /data/data/com.my.app/cache
     * getDatabasePath(“test”) = /data/data/com.my.app/databases/test
     * getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
     * getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
     * getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
     * getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
     * getFilesDir() = /data/data/com.my.app/files
     * <p>
     * TODO 清理应用缓存 & 清理应用数据 & 应用卸载 & 应用覆盖安装 都清理了哪些目录
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_files_btn: {
                try {
//                    OutputStream saveFileOut = getActivity().openFileOutput("file_data", Context.MODE_WORLD_WRITEABLE);
                    OutputStream saveFileOut = new FileOutputStream(new File(getActivity().getFilesDir(), "file_data"));
                    saveFileOut.write("this is files \n这里是文件夹".getBytes("utf-8"));
                    saveFileOut.flush();
                    saveFileOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.save_caches_btn: {
                OutputStream saveFileOut = null;
                try {
                    saveFileOut = new FileOutputStream(new File(getActivity().getCacheDir(), "cache_file"));
                    saveFileOut.write("this is caches \n这里是缓存文件".getBytes("utf-8"));
                    saveFileOut.flush();
                    saveFileOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.get_files_btn: {
                try {
                    InputStream getFileIn = getActivity().openFileInput("file_data");
                    byte[] buffer = new byte[getFileIn.available()];
                    getFileIn.read(buffer);
                    getFileIn.close();
                    ToastUtil.showMessage(new String(buffer));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.get_caches_btn: {
                try {
                    InputStream getCacheIn = new FileInputStream(new File(getActivity().getCacheDir(), "cache_file"));
                    byte[] buffer = new byte[getCacheIn.available()];
                    getCacheIn.read(buffer);
                    getCacheIn.close();
                    ToastUtil.showMessage(new String(buffer));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.same_path_btn: {
                AlertDialog builder = new AlertDialog.Builder(getActivity())
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setMessage(
                                "Environment.getDataDirectory(): \n" + Environment.getDataDirectory().getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "Environment.getDownloadCacheDirectory(): \n" + Environment.getDownloadCacheDirectory().getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "Environment.getExternalStorageDirectory(): \n" + Environment.getExternalStorageDirectory().getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "Environment.getExternalStoragePublicDirectory(“test”): \n" + Environment.getExternalStoragePublicDirectory("test") + "\n" +
                                        "*********\n" +
                                        "Environment.getRootDirectory(): \n" + Environment.getRootDirectory().getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "getPackageCodePath(): \n" + getActivity().getPackageCodePath() + "\n" +
                                        "*********\n" +
                                        "getPackageResourcePath(): \n" + getActivity().getPackageResourcePath() + "\n" +
                                        "*********\n" +
                                        "getCacheDir(): \n" + getActivity().getCacheDir().getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "getDatabasePath(“test”): \n" + getActivity().getDatabasePath("test").getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "getDir(“test”, Context.MODE_PRIVATE): \n" + getActivity().getDir("test", Context.MODE_PRIVATE).getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "getExternalCacheDir(): \n" + getActivity().getExternalCacheDir().getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "getExternalFilesDir(“test”): \n" + getActivity().getExternalFilesDir("test").getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "getExternalFilesDir(null): \n" + getActivity().getExternalFilesDir(null).getAbsolutePath() + "\n" +
                                        "*********\n" +
                                        "getFilesDir(): \n" + getActivity().getFilesDir().getAbsolutePath()
                        ).show();
            }
        }
    }
}
