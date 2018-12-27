//
// Created by surface on 2018/12/24.
//
/**
 * 通过：
 * externalNativeBuild {
        cmake {
            path "src/main/cmake/CMakeLists.txt"
        }
    }
    配合CMakeLists.txt直接编译运行
    与ndkBuild方式只能存其一
 */
#include <jni.h>

//extern "C"
jstring
Java_com_cc_xsl_coolweather_CMakeTest_stringFromC(
        JNIEnv *env,
        jobject jo) {
    return (*env)->NewStringUTF(env, "hello cmake jni C !");
}
