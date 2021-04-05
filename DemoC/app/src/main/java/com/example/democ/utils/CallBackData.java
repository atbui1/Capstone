package com.example.democ.utils;

public interface CallBackData<T> {
    void onSuccess(T t);
    void onFail(String msgFail);
}
