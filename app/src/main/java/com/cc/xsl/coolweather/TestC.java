package com.cc.xsl.coolweather;

/**
 * 通过：
 * externalNativeBuild {
 * ndkBuild {
 * path "src/main/cpp/Android.mk"
 * }
 * }
 * 配合Android.mk直接编译运行
 * 与cmake方式只能存其一
 * <p>
 * 生成头文件：
 * C:\Users\surface\Documents\Android\GitHub\CoolWearther\app\src\main\java>
 * javah -d ../jni com.cc.xsl.coolweather.TestC
 */
public class TestC {
    public static native String fromC();

    static {
        System.loadLibrary("test");
    }
}
