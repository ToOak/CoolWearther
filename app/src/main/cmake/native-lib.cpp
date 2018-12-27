#include <jni.h>
#include <string>
#include <android/log.h>

#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "oak", __VA_ARGS__))
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "oak", __VA_ARGS__))
#define LOGV(...)  ((void)__android_log_print(ANDROID_LOG_VERBOSE, "oak", __VA_ARGS__))

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
extern "C" JNIEXPORT jstring JNICALL
Java_com_cc_xsl_coolweather_CMakeTest_stringFromCpp(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello CMake from C++";
    LOGE("cmake string from cpp");
    return env->NewStringUTF(hello.c_str());
}
