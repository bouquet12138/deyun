package com.example.common_lib.model;

import android.util.Log;

import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.OkHttpUtil;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.StoreBean;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class StoreModel {

    private static final String TAG = "StoreModel";

    /**
     * 初始化商店信息
     */
    public void initStoreInfo(String province, String city, String district, String store_name, OnGetInfoListener<BaseBean<List<StoreBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("province", province);
            jsonObject.put("city", city);
            jsonObject.put("district", district);
            jsonObject.put("store_name", store_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getStoreList("init_store_info", jsonObject.toString(), listener);

    }

    /**
     * 刷新商店信息
     *
     * @param store_id
     * @param province
     * @param city
     * @param district
     * @param store_name
     * @param listener
     */
    public void refreshStoreInfo(int store_id, String province, String city, String district, String store_name, OnGetInfoListener<BaseBean<List<StoreBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("store_id", store_id);
            jsonObject.put("province", province);
            jsonObject.put("city", city);
            jsonObject.put("district", district);
            jsonObject.put("store_name", store_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getStoreList("refresh_store_info", jsonObject.toString(), listener);
    }

    /**
     * 下拉加载更多商店信息
     *
     * @param store_id
     * @param province
     * @param city
     * @param district
     * @param store_name
     * @param listener
     */
    public void loadMoreStoreInfo(int store_id, String province, String city, String district, String store_name, OnGetInfoListener<BaseBean<List<StoreBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("store_id", store_id);
            jsonObject.put("province", province);
            jsonObject.put("city", city);
            jsonObject.put("district", district);
            jsonObject.put("store_name", store_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getStoreList("load_more_store_info", jsonObject.toString(), listener);
    }

    /**
     * 得到商店列表信息
     *
     * @param url
     * @param json
     * @param listener
     */
    private void getStoreList(String url, String json, OnGetInfoListener<BaseBean<List<StoreBean>>> listener) {
        Log.d(TAG, "getStoreList: " + json);
        OkHttpUtil.postJson(ServerInfo.getServerAddress(url), json, new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<StoreBean>> baseBean = BaseBean.analysisBaseBeanList(responseData, StoreBean.class);

                if (baseBean != null)
                    listener.onResult(baseBean);//传递出去
                else
                    listener.onNetError();

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                listener.onComplete();
                listener.onNetError();//网络错误
            }
        });
    }

}
