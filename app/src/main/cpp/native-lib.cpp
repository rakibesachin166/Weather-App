#include <jni.h>
#include <string>



extern "C"
JNIEXPORT jstring JNICALL
Java_com_sacdev_mvvm_utils_Application_getApiKey(
        JNIEnv *env, jobject thiz) {
    std::string hello = "24b9099818f42aa9456d75c64037f9a6";
    return env->NewStringUTF(hello.c_str());
}




