package com.example.baselib.listener;

public interface OnGetInfoListener<T> {

    void onComplete();

    void onNetError();

    void onResult(T info);

}
