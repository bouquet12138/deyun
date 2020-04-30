package com.example.baselib.util;

/**
 * 网络变化监听
 */
public interface NetChangeObserver {
    /**
     * 网络连接成功
     */
    void onConnected(NetType type);
    /**
     * 网络断开
     */
    void onDisConnected();
}

