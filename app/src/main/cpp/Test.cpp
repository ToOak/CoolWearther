//
// Created by surface on 2018/12/27.
//
/**
 * 通过：
 * externalNativeBuild {
        ndkBuild {
            path "src/main/cpp/Android.mk"
        }
    }
    配合Android.mk直接编译运行
    与cmake方式只能存其一
 */
#include "com_cc_xsl_coolweather_TestC.h"

JNIEXPORT jstring JNICALL Java_com_cc_xsl_coolweather_TestC_fromC
        (JNIEnv *env, jclass jc) {
    return env->NewStringUTF("Test ndk C !");
}
