package com.example.common_lib.model;

import android.util.Log;

import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.GsonUtil;
import com.example.baselib.util.OkHttpUtil;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * 只能本包中访问
 */
class ModelUtil {

    private static final String TAG = "ModelUtil";

    static void postJsonBean(Object obj, String _address, OnGetInfoListener<BaseBean> listener) {
        String json = GsonUtil.GsonString(obj);
        postJson(json, _address, listener);
    }


    static void postJson(String json, String _address, OnGetInfoListener<BaseBean> listener) {
        String address = ServerInfo.getServerAddress(_address);//地址
        OkHttpUtil.postJson(address, json, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                listener.onComplete();
                listener.onNetError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean info = GsonUtil.GsonToBean(responseData, BaseBean.class);
                if (info == null)
                    listener.onNetError();
                else
                    listener.onResult(info);//传递出去
            }
        });
    }

}
