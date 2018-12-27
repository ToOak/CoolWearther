package com.cc.xsl.coolweather;

/**
 * 通过：
 * externalNativeBuild {
 * cmake {
 * path "src/main/cmake/CMakeLists.txt"
 * }
 * }
 * 配合CMakeLists.txt直接编译运行
 * 与ndkBuild方式只能存其一
 * <p>
 * 生成头文件：
 * C:\Users\surface\Documents\Android\GitHub\CoolWearther\app\src\main\java>
 * javah -d ../cmake com.cc.xsl.coolweather.CMakeTest
 */
public class CMakeTest {
    public static native String stringFromC();

    public static native String stringFromCpp();

    static {
        System.loadLibrary("native-lib");
    }
}
