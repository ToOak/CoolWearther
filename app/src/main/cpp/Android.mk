LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := test
LOCAL_SRC_FILES := Test.cpp

# for logging
LOCAL_LDLIBS    += -llog

include $(BUILD_SHARED_LIBRARY)