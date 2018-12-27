#include <jni.h>
#include <string>

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
    return env->NewStringUTF(hello.c_str());
}
